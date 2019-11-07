package eu.venthe.jpaexploration.postgresql.repository.impl;

import eu.venthe.jpaexploration.CandidateRepository;
import eu.venthe.jpaexploration.TestRepository;
import eu.venthe.jpaexploration.batch.SafeBatchPersistenceManager;
import eu.venthe.jpaexploration.model.TestEntity;
import eu.venthe.jpaexploration.model.TestEntityDto;
import eu.venthe.jpaexploration.postgresql.config.PostgreSQLConfig;
import eu.venthe.jpaexploration.postgresql.repository.PostgreSQLCRUDRepository;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.util.List;
import java.util.stream.Stream;

@Repository
@ToString
@Slf4j
public class PostgreSQLRepository implements TestRepository<TestEntity>, CandidateRepository<TestEntity> {

    @ToString.Exclude
    private final PostgreSQLCRUDRepository repositoryCRUD;

    @ToString.Exclude
    @PersistenceUnit(unitName = PostgreSQLConfig.PERSISTENCE_UNIT_NAME)
    private final EntityManagerFactory entityManagerFactory;

    @ToString.Exclude
    private SafeBatchPersistenceManager safeBatchPersistenceManager;

    private final int batchSize;

    public PostgreSQLRepository(PostgreSQLCRUDRepository repositoryCRUD,
                                EntityManagerFactory entityManagerFactory,
                                SafeBatchPersistenceManager safeBatchPersistenceManager,
                                @Value("${spring.jpa.properties.hibernate.jdbc.batch_size}") int batchSize) {
        this.repositoryCRUD = repositoryCRUD;
        this.entityManagerFactory = entityManagerFactory;
        this.safeBatchPersistenceManager = safeBatchPersistenceManager;
        this.batchSize = batchSize;

        log.debug("Batch size is {}", batchSize);
    }

    @Override
    @Transactional
    public void truncate() {
        repositoryCRUD.truncate();
    }

    @Override
    @Transactional
    public void batchedManualSave(List<TestEntity> entities) {
        safeBatchPersistenceManager.manualSaveAllInBatchedTransactions(entityManagerFactory, entities, batchSize);
    }

    @Override
    @Transactional
    public Iterable<TestEntity> springDataSaveAll(List<TestEntity> entities) {
        return repositoryCRUD.saveAll(entities);
    }

    @Override
    @Transactional
    public Iterable<TestEntity> springDataLoadAll() {
        return repositoryCRUD.findAll();
    }

    @Override
    @Transactional
    public Stream<TestEntity> springDataLoadAllAsStream() {
        return repositoryCRUD.getAll();
    }

    @Override
    public Stream<TestEntityDto> getAllDto() {
        return repositoryCRUD.getAllDto();
    }

    @Override
    @Transactional
    public void batchedSaveStreamJooq(Stream<TestEntity> entities) {
        safeBatchPersistenceManager.batchedSaveStreamJooq(entityManagerFactory, entities, batchSize);
    }

    @Override
    @Transactional
    public void batchedSaveStreamGuava(Stream<TestEntity> entities) {
        safeBatchPersistenceManager.batchedSaveStreamGuava(entityManagerFactory, entities, batchSize);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public TestEntity springDataSave(TestEntity e) {
        return repositoryCRUD.save(e);
    }

    @Override
    @Transactional
    public void batchSaveStream(Stream<TestEntity> entityStream) {
        batchedSaveStreamGuava(entityStream);
    }

    @Override
    @Transactional
    public Stream<TestEntity> streamAll() {
        return springDataLoadAllAsStream();
    }
}
