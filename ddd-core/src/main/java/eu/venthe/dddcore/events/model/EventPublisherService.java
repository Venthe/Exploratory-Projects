package eu.venthe.dddcore.events.model;

public interface EventPublisherService {
    void raiseEvent(Event event);
}
