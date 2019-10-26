package eu.venthe.jpaexploration.postgresql.repository.impl;

import eu.venthe.jpaexploration.TestRepository;
import eu.venthe.jpaexploration.postgresql.repository.PostgreSQLCRUDRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@AllArgsConstructor
@Repository
public class PostgreSQLRepository implements TestRepository {
    private final PostgreSQLCRUDRepository repositoryCRUD;
}
