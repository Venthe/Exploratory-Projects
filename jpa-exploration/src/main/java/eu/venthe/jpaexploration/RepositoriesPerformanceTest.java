package eu.venthe.jpaexploration;

import eu.venthe.jpaexploration.model.TestEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static java.time.Duration.between;
import static java.time.LocalDateTime.now;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;

@AllArgsConstructor
@Service
@Slf4j
public class RepositoriesPerformanceTest {
    private final static Long ENTITIES_COUNT = 30000L;
    private final Set<TestRepository> repositories;
    private final SpringTransactionService transactionService;

    @EventListener({ContextRefreshedEvent.class})
    public void onStart() {
        log.debug("Run database tests - start");
        repositories.forEach(this::runDatabaseTests);
        log.debug("Run database tests - stop");
    }

    private void runDatabaseTests(TestRepository repository) {
        /*
        // Slower by ~3%, clunkier to use compared to Guava. Smaller footprint?
        // ~26s on 30000 entities
        logTime(() -> RepositoriesPerformanceTest.truncate(repository));
        logTime(batchedSaveViaSpringDataJooq(repository));
        */

        // Fastest, elegant. But Guava, how large is it?
        // ~25s on 30000 entities
        logTime(() -> RepositoriesPerformanceTest.truncate(repository));
        logTime(batchedSaveViaSpringDataGuava(repository));

        /*
        // Abysmally slow.
        // 9.751s with 300 entities, extrapolating to 16m 15s with 30000 entities
        logTime(() -> RepositoriesPerformanceTest.truncate(repository));
        logTime(saveSingleViaSpringData(repository));

        // Not interesting, crashes on ~24mb of Xmx/Xms with 30000 entities
        logTime(() -> RepositoriesPerformanceTest.truncate(repository));
        logTime(saveAllViaSpringData(repository));

        // Not interesting, crashes on ~24mb of Xmx/Xms with 30000 entities
        logTime(() -> RepositoriesPerformanceTest.truncate(repository));
        logTime(manualSaveAll(repository));
        */

        transactionService.executeInTransaction(() -> {
            Stream<TestEntity> result = logTime(getLoadAllStream(repository));
            logTime(collectResult(result));
        });

        /*
        // Not interesting, crashes on ~24mb of Xmx/Xms with 30000 entities
        logTime(loadAll(repository));
         */
    }

    private Runnable manualSaveAll(TestRepository repository) {
        List<TestEntity> entities = logTime(() -> createEntities(ENTITIES_COUNT))
                .collect(Collectors.toList());
        log.debug("saving {} via manualSaveAll. repository={}", ENTITIES_COUNT, repository);
        return () ->
                repository.batchedManualSave(entities);
    }

    private Supplier<Iterable<TestEntity>> loadAll(TestRepository repository) {
        log.debug("loadAll");
        return repository::springDataLoadAll;
    }

    private Supplier<List<TestEntity>> collectResult(Stream<TestEntity> result) {
        log.debug("loadAllStream - collect");
        return () -> result.collect(Collectors.toList());
    }

    private Supplier<Stream<TestEntity>> getLoadAllStream(TestRepository repository) {
        log.debug("loadAllStream - query");
        return repository::springDataLoadAllAsStream;
    }

    private Supplier<Iterable<TestEntity>> saveAllViaSpringData(TestRepository repository) {
        List<TestEntity> entities = logTime(() -> createEntities(ENTITIES_COUNT))
                .collect(Collectors.toList());
        log.debug("saving {} via saveAllViaSpringData. repository={}", ENTITIES_COUNT, repository);
        return () ->
                repository.springDataSaveAll(entities);
    }

    @Transactional
    protected Runnable batchedSaveViaSpringDataJooq(TestRepository repository) {
        Stream<TestEntity> entities = logTime(() -> createEntities(ENTITIES_COUNT));
        log.debug("saving {} via batchedSaveViaSpringDataJooq. repository={}", ENTITIES_COUNT, repository);
        return () -> repository.batchedSaveStreamJooq(entities);
    }

    @Transactional
    protected Runnable batchedSaveViaSpringDataGuava(TestRepository repository) {
        Stream<TestEntity> entities = logTime(() -> createEntities(ENTITIES_COUNT));
        log.debug("saving {} via batchedSaveViaSpringDataGuava. repository={}", ENTITIES_COUNT, repository);
        return () -> repository.batchedSaveStreamGuava(entities);
    }


    private Runnable saveSingleViaSpringData(TestRepository repository) {
        Stream<TestEntity> entities = logTime(() -> createEntities(ENTITIES_COUNT));
        log.debug("saving {} via saveSingleViaSpringData. repository={}", ENTITIES_COUNT, repository);
        return () -> entities.forEach(repository::springDataSave);
    }

    private static void truncate(TestRepository repository) {
        log.debug("truncating. repository={}", repository);
        repository.truncate();
    }

    private static Stream<TestEntity> createEntities(Long howMany) {
        log.debug("creating {} entities", howMany);
        return LongStream.range(0, howMany)
                .boxed()
                .map(i -> TestEntity.builder()
                        .numeric(Long.parseLong(randomNumeric(7)))
                        .character(randomAlphanumeric(3))
                        .build()
                );
    }

    private static void logTime(Runnable supplier) {
        logTime(() -> {
            supplier.run();
            return null;
        });
    }

    private static <T> T logTime(Supplier<T> supplier) {
        LocalDateTime startTime = now();
        T result = supplier.get();
        LocalDateTime endTime = now();
        log.debug("took {} second(s)", between(startTime, endTime).toMillis() / 1000d);

        return result;
    }
}
