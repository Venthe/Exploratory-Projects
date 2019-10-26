package eu.venthe.jpaexploration;

import eu.venthe.jpaexploration.model.TestEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Stream;

public interface TestRepository<T> {
    void truncate();
    Iterable<TestEntity> springDataSaveAll(List<T> entities);
    T springDataSave(T entities);
    Iterable<T> springDataLoadAll();
    Stream<T> springDataLoadAllAsStream();
    void batchedManualSave(List<T> entities);
    void batchedSaveStreamJooq(Stream<T> entities);
    void batchedSaveStreamGuava(Stream<T> entities);
}
