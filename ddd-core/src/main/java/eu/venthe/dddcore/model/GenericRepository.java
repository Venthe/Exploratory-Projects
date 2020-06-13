package eu.venthe.dddcore.model;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface GenericRepository<AGGREGATE_ROOT_TYPE extends AbstractAggregateRoot> {
    Optional<AGGREGATE_ROOT_TYPE> load(UUID id);

    AGGREGATE_ROOT_TYPE save(AGGREGATE_ROOT_TYPE aggregate);

    Collection<AGGREGATE_ROOT_TYPE> saveAll(Collection<AGGREGATE_ROOT_TYPE> aggregates);

    void remove(UUID id);
}
