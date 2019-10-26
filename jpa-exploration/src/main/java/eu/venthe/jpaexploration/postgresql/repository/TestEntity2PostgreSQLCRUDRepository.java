package eu.venthe.jpaexploration.postgresql.repository;

import eu.venthe.jpaexploration.model.TestEntity2;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.stream.Stream;

@Repository
public interface TestEntity2PostgreSQLCRUDRepository extends CrudRepository<TestEntity2, String> {
    @Modifying
    @Transactional
    @Query(value = "TRUNCATE TABLE test_entity_2", nativeQuery = true)
    void truncate();

    @Transactional
    @Query("SELECT e FROM test_entity_2 e")
    Stream<TestEntity2> streamAll();
}
