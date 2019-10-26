package eu.venthe.jpaexploration.mssql.repository.impl;

import eu.venthe.jpaexploration.TestRepository;
import eu.venthe.jpaexploration.model.TestEntity;
import eu.venthe.jpaexploration.mssql.repository.MSSQLCRUDRepository;
import lombok.AllArgsConstructor;
import lombok.ToString;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Repository;

import java.util.List;

@AllArgsConstructor
@Repository
@ToString
@ConditionalOnExpression("false")
public class MSSQLRepository {
    @ToString.Exclude
    private final MSSQLCRUDRepository repositoryCRUD;

    public void truncate() {
        repositoryCRUD.truncate();
    }

    public Iterable<TestEntity> springDataSave(List<TestEntity> entities) {
        entities.forEach(repositoryCRUD::save);
        return entities;
    }

    public Iterable<TestEntity> springDataSaveAll(List<TestEntity> entities) {
        return repositoryCRUD.saveAll(entities);
    }
}
