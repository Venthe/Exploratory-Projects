package eu.venthe.reactive;

import eu.venthe.reactive.utils.LogBeanConstruction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.DirectProcessor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.text.MessageFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.function.Consumer;
import java.util.stream.Stream;

@SpringBootApplication
public class ReactiveApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReactiveApplication.class, args);
    }
}

@Component
@Slf4j
@LogBeanConstruction
@RequiredArgsConstructor
class Application {
    private final ReservationRepository reservationRepository;

    @EventListener(ApplicationStartedEvent.class)
    public void onStart() {
        var other = Flux.just("Josh", "Cornelia", "Sawyer", "Violetta", "Sthephane", "Olga", "Sebastien", "Madhura")
                .map(Reservation::of)
                .flatMap(reservationRepository::save);

        reservationRepository.deleteAll()
                .thenMany(
                        other
                )
                .thenMany(reservationRepository.findAll())
                .map(Object::toString)
                .subscribe(log::info);
    }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class GreetingsRequest {
    private String name;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class GreetingsResponse {
    private String greeting;
}

@Component
class IntervalMessageProducer {
    Flux<GreetingsResponse> produce(GreetingsRequest name) {
        return Flux.fromStream(Stream.generate(() -> "Hello " + name.getName() + "@" + Instant.now()))
                .map(GreetingsResponse::new)
                .delayElements(Duration.ofSeconds(1));
    }
}

@Slf4j
@Component
class EventDriver {
    private static DirectProcessor<Object> EVENTS = DirectProcessor.create();

    @EventListener(Object.class)
    public void onEvent(Object event) {
        log.info(MessageFormat.format("Event {0} is ready to be pushed into processor.", event.toString()));
        EVENTS.onNext(event);
    }

    @EventListener(ApplicationStartedEvent.class)
    public void logger() {
        EVENTS.map(Object::toString).subscribe(e -> log.info(MessageFormat.format("Event {0} received by processor.", e)));
    }

    public Flux<Object> getEvents() {
        log.info("Requesting processor.");
        return EVENTS
                .doOnNext(l -> log.info(MessageFormat.format("Processing subscribed event {0}", l.toString())));
    }
}

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
    class MyEvent {
        private String name;
    }
}

interface ReservationRepository extends ReactiveCrudRepository<Reservation, String> {
}

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
class Reservation {
    @Id
    private String id;
    private String name;

    public static Reservation of(String name) {
        return new Reservation(null, name);
    }
}
