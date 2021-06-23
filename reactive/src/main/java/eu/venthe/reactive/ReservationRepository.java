package eu.venthe.reactive;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

interface ReservationRepository extends ReactiveCrudRepository<Reservation, String> {
}
