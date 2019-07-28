package eu.venthe.dddcore.model;

import eu.venthe.dddcore.events.model.Event;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public abstract class AbstractAggregateRoot extends AbstractEntity {
    protected Collection<Event> events = new ArrayList<>();

    final protected Collection<Event> onTransactionEnd() {
        Collection<Event> toReturn = Collections.unmodifiableCollection(events);
        events.clear();
        return toReturn;
    }
}
