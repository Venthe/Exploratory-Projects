package eu.venthe.reactive;

import eu.venthe.reactive.utils.LogBeanConstruction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
@Slf4j
@LogBeanConstruction
@RequiredArgsConstructor
class Application {
    private final ReservationRepository reservationRepository;

    @EventListener(ApplicationStartedEvent.class)
    public void onStart() {
//        var other = Flux.just("Josh", "Cornelia", "Sawyer", "Violetta", "Sthephane", "Olga", "Sebastien", "Madhura")
//                .map(Reservation::of)
//                .flatMap(reservationRepository::save);
//
//        reservationRepository.deleteAll()
//                .thenMany(
//                        other
//                )
//                .thenMany(reservationRepository.findAll())
//                .map(Object::toString)
//                .subscribe(log::info);
    }
}
