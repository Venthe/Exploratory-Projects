package eu.venthe.jpaperfcompare.repository;

import eu.venthe.jpaperfcompare.documents.PerfDaySimple;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SimpleRepository extends MongoRepository<PerfDaySimple, Long> {
}
