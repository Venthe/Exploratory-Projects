package eu.venthe.jpaexploration.mssql.repository.impl;

import eu.venthe.jpaexploration.TestRepository;
import eu.venthe.jpaexploration.mssql.repository.MSSQLCRUDRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@AllArgsConstructor
@Repository
public class MSSQLRepository implements TestRepository {
    private final MSSQLCRUDRepository repositoryCRUD;
}
