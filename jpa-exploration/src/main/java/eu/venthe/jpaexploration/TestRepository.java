package eu.venthe.jpaexploration;

import eu.venthe.jpaexploration.model.TestEntity;
import eu.venthe.jpaexploration.model.TestEntityDto;

import java.util.List;
import java.util.stream.Stream;

public interface TestRepository<T> extends CandidateRepository<T>, Truncatable {
    Iterable<TestEntity> springDataSaveAll(List<T> entities);

    T springDataSave(T entities);

    Iterable<T> springDataLoadAll();

    void batchedManualSave(List<T> entities);

    void batchedSaveStreamJooq(Stream<T> entities);

    void batchedSaveStreamGuava(Stream<T> entities);

    Stream<T> springDataLoadAllAsStream();

    Stream<TestEntityDto> getAllDto();
}
