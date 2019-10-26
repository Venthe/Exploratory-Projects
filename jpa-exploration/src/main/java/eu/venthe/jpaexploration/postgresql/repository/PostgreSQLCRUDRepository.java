package eu.venthe.jpaexploration.postgresql.repository;

import eu.venthe.jpaexploration.model.Test;
import org.springframework.data.repository.CrudRepository;

public interface PostgreSQLCRUDRepository extends CrudRepository<Test, Test.Identity> {
}
