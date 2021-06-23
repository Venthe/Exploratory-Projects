package eu.venthe.reactive;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.DirectProcessor;
import reactor.core.publisher.Flux;

import java.text.MessageFormat;

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
