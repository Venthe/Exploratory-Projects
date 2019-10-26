package eu.venthe.jpaexploration.postgresql.repository;

import eu.venthe.jpaexploration.model.TestEntity;
import eu.venthe.jpaexploration.model.TestEntityDto;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.stream.Stream;

@Repository
public interface PostgreSQLCRUDRepository extends CrudRepository<TestEntity, String> {
    @Modifying
    @Transactional
    @Query(value = "TRUNCATE TABLE test_entity", nativeQuery = true)
    void truncate();

    @Transactional
    @Query("SELECT e FROM test_entity e")
    Stream<TestEntity> getAll();

    @Transactional
    @Query(value =
            "SELECT " +
                    " test_entity.id as id, " +
                    " test_entity.character as character, " +
                    " test_entity.numeric as numeric, " +
                    " test_entity.datetime as datetime " +
                    " FROM test_entity", nativeQuery = true)
    Stream<TestEntityDto> getAllDto();
}
