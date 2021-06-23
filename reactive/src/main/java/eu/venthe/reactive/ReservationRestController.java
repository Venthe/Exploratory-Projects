package eu.venthe.reactive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.Loggers;
import reactor.util.function.Tuple2;

import java.util.function.Consumer;

@Slf4j
@RestController
@RequiredArgsConstructor
class ReservationRestController {
    private final ReservationRepository reservationRepository;
    private final IntervalMessageProducer intervalMessageProducer;
    private final ApplicationEventPublisher eventDispatcher;
    private final EventDriver eventDriver;

    @GetMapping("/reservations")
    public Flux<Reservation> getReservations() {
        return reservationRepository.findAll();
    }

    @GetMapping("/event")
    public void getReservationss() {
        eventDispatcher.publishEvent(new MyEvent("Test"));
    }

    @GetMapping(value = "/wait-for-event/{name}")
    public Mono<String> performAction(@PathVariable String name) {
        return eventDriver.getEvents()
                .doOnNext(dispatchEvent(name))
                .map(Object::toString)
                .take(1)
                .single()
                .log();
    }

    @GetMapping("/wtf/{name}")
    public String wtf(@PathVariable String name) {
        return Mono.from(
                eventDriver.getEvents()
                        .map(Object::toString)
                        .filter(b -> b.contains(name))
                        .log("Event source")
        )
                .zipWith(Mono.just(name)
                        .map(MyEvent::new)
                        .doOnNext(eventDispatcher::publishEvent)
                        .log("Publisher"))
                .map(Tuple2::getT1)
                .log("Result")
                .block();
    }

    private Consumer<Object> dispatchEvent(@PathVariable String name) {
        return discard -> {
            final MyEvent event = new MyEvent(name);
            ReservationRestController.log.info("Publishing: " + event);
            eventDispatcher.publishEvent(event);
        };
    }

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE, value = "/interval/{name}")
    public Flux<GreetingsResponse> getReservations(@PathVariable String name) {
        return intervalMessageProducer.produce(new GreetingsRequest(name));
    }

    @Bean
    RouterFunction<ServerResponse> route(ReservationRepository reservationRepository) {
        return RouterFunctions.route()
                .GET("/functional/reservations", request ->
                        ServerResponse.ok()
                                .body(reservationRepository.findAll(), Reservation.class)
                )
                .build();
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    static
    class MyEvent {
        private String name;
    }
}
