package eu.venthe.dddcore.events.model;

import java.util.Optional;

public interface Event<PAYLOAD_TYPE> {
    String getKey();
    Optional<PAYLOAD_TYPE> getPayload();
    void subscribe(EventListener eventListener);
    void unsubscribe(EventListener eventListener);
}
