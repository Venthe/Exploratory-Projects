package eu.venthe.jpaexploration.batch;

import com.google.common.collect.Iterators;
import lombok.extern.slf4j.Slf4j;
import org.jooq.lambda.Seq;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class SafeBatchPersistenceManager {

    @Transactional
    public <T> void batchedSaveStreamJooq(EntityManagerFactory entityManagerFactory,
                                          Stream<T> entities,
                                          int batchSize) {
        Seq.seq(entities)
                .zipWithIndex()
                .groupBy(tuple -> tuple.v2 / batchSize)
                .forEach((index, batch) -> {
                    List<T> collectedResults = batch.stream()
                            .map(tuple -> tuple.v1)
                            .collect(Collectors.toList());
                    manualSaveAllInSingleTransaction(entityManagerFactory, collectedResults);
                });
    }

    @Transactional
    public <T> void batchedSaveStreamGuava(EntityManagerFactory entityManagerFactory,
                                           Stream<T> entities,
                                           int batchSize) {
        Iterators.partition(entities.iterator(), batchSize)
                .forEachRemaining(batch ->
                        manualSaveAllInSingleTransaction(entityManagerFactory, batch));
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public <T> void manualSaveAllInBatchedTransactions(EntityManagerFactory entityManagerFactory,
                                                       List<T> entities,
                                                       int batchSize) {
        manualSaveAllProvider(entityManagerFactory, (EntityManager entityManager, EntityTransaction entityTransaction) -> {
            Iterator<T> iterator = entities.iterator();
            int cont = 0;
            while (iterator.hasNext()) {
                entityManager.persist(iterator.next());
                cont++;
                if (cont % batchSize == 0) {
                    log.trace("Persisting batch. batchSize={}", batchSize);
                    entityManager.flush();
                    entityManager.clear();
                    entityTransaction.commit();
                    entityTransaction.begin();
                }
            }
            log.trace("Persisting remaining entities");
        });
    }


    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public <T> void manualSaveAllInSingleTransaction(EntityManagerFactory entityManagerFactory,
                                                     List<T> entities) {
        log.trace("Persisting batch. batchSize={}", entities.size());
        manualSaveAllProvider(entityManagerFactory,
                (EntityManager entityManager, EntityTransaction entityTransaction) -> {
                    for (T entity : entities) {
                        entityManager.persist(entity);
                    }
                });
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void manualSaveAllProvider(EntityManagerFactory entityManagerFactory,
                                      BiConsumer<EntityManager, EntityTransaction> biConsumer) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        biConsumer.accept(entityManager, entityTransaction);

        entityManager.flush();
        entityManager.clear();
        entityTransaction.commit();
        entityManager.close(); // To avoid saturating Hikari connection pool
    }
}
