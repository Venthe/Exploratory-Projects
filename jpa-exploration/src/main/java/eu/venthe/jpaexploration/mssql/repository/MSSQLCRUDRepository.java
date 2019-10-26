package eu.venthe.jpaexploration.mssql.repository;

import eu.venthe.jpaexploration.model.Test;
import org.springframework.data.repository.CrudRepository;

public interface MSSQLCRUDRepository extends CrudRepository<Test, Test.Identity> {
}
