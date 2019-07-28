package eu.venthe.dddcore.events.model;

public interface EventListener {
    void onEventRaised(Event event);
}
