package eu.venthe.jpaexploration.mssql.repository;

import eu.venthe.jpaexploration.model.TestEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface MSSQLCRUDRepository extends CrudRepository<TestEntity, String> {
    @Modifying
    @Transactional
    @Query(value = "TRUNCATE TABLE dbo.test_entity", nativeQuery = true)
    void truncate();
}
