package eu.venthe.jpaexploration;

import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Stream;

/**
 * Candidates for production.
 */
public interface CandidateRepository<T> extends Truncatable {
    @Transactional
    void batchSaveStream(Stream<T> entityStream);

    @Transactional
    Stream<T> streamAll();
}
