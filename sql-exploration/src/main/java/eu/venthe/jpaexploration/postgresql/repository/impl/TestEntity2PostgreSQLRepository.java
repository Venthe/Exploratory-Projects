package eu.venthe.jpaexploration.postgresql.repository.impl;

import eu.venthe.jpaexploration.CandidateRepository;
import eu.venthe.jpaexploration.batch.SafeBatchPersistenceManager;
import eu.venthe.jpaexploration.model.TestEntity2;
import eu.venthe.jpaexploration.postgresql.config.PostgreSQLConfig;
import eu.venthe.jpaexploration.postgresql.repository.TestEntity2PostgreSQLCRUDRepository;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.util.stream.Stream;

@Repository("testEntity2PostgreSQLRepository")
@ToString
@Slf4j

public class TestEntity2PostgreSQLRepository implements CandidateRepository<TestEntity2> {
    @ToString.Exclude
    @PersistenceUnit(unitName = PostgreSQLConfig.PERSISTENCE_UNIT_NAME)
    private final EntityManagerFactory entityManagerFactory;

    private final int batchSize;

    @ToString.Exclude
    private final TestEntity2PostgreSQLCRUDRepository crudRepository;

    @ToString.Exclude
    private SafeBatchPersistenceManager safeBatchPersistenceManager;

    public TestEntity2PostgreSQLRepository(EntityManagerFactory entityManagerFactory,
                                           @Value("${spring.jpa.properties.hibernate.jdbc.batch_size}") int batchSize,
                                           TestEntity2PostgreSQLCRUDRepository crudRepository,
                                           SafeBatchPersistenceManager safeBatchPersistenceManager) {
        this.entityManagerFactory = entityManagerFactory;
        this.batchSize = batchSize;
        this.crudRepository = crudRepository;
        this.safeBatchPersistenceManager = safeBatchPersistenceManager;

        log.debug("Batch size is {}", batchSize);
    }

    @Override
    @Transactional
    public void truncate() {
        crudRepository.truncate();
    }

    @Override
    public void batchSaveStream(Stream<TestEntity2> entityStream) {
        safeBatchPersistenceManager.batchedSaveStreamGuava(entityManagerFactory, entityStream, batchSize);
    }

    @Override
    public Stream<TestEntity2> streamAll() {
        return crudRepository.streamAll();
    }
}
