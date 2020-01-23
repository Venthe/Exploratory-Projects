package eu.venthe.jpaperfcompare.repository;

import eu.venthe.jpaperfcompare.entity.PerfDaySimple;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SimpleRepository extends CrudRepository<PerfDaySimple, Long> {
}
