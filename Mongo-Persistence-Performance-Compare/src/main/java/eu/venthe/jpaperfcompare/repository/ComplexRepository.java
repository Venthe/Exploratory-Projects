package eu.venthe.jpaperfcompare.repository;

import eu.venthe.jpaperfcompare.documents.PerfDayComplex;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.stream.Stream;

public interface ComplexRepository extends MongoRepository<PerfDayComplex, Long> {
}
