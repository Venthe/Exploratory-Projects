package eu.venthe.reactive;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.Instant;
import java.util.stream.Stream;

@Component
class IntervalMessageProducer {
    Flux<GreetingsResponse> produce(GreetingsRequest name) {
        return Flux.fromStream(Stream.generate(() -> "Hello " + name.getName() + "@" + Instant.now()))
                .map(GreetingsResponse::new)
                .delayElements(Duration.ofSeconds(1));
    }
}
