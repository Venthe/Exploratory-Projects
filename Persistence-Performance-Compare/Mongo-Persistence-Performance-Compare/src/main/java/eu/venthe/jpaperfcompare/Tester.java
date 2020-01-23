package eu.venthe.jpaperfcompare;

import com.google.common.collect.Iterators;
import eu.venthe.jpaperfcompare.documents.PerfDayComplex;
import eu.venthe.jpaperfcompare.documents.PerfDaySimple;
import eu.venthe.jpaperfcompare.documents.Period;
import eu.venthe.jpaperfcompare.repository.ComplexRepository;
import eu.venthe.jpaperfcompare.repository.SimpleRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static java.text.MessageFormat.format;

@Component
@AllArgsConstructor
@Slf4j
public class Tester {
    private final ComplexRepository complexRepository;
    private final SimpleRepository simpleRepository;

    @PostConstruct
    protected void test2() throws InterruptedException {
        TimeUnit.SECONDS.sleep(1);
        /*
         The CSO statistics include entities that suspended their operations, entities which ceased operations,
         but information about this fact did not reach the Central Statistical Office and entities
         that are not entrepreneurs (foundations, associations).
        */
        final long companiesInPoland2015 = 4_155_328;
        performTest(100L);
        performTest(17_000L);
        performTest(companiesInPoland2015 / 100);
        performTest(50_000L);
        performTest(companiesInPoland2015 / 10);
        performTest(companiesInPoland2015);
        performTest(companiesInPoland2015 * 2);
    }

    private void performTest(Long testData) {
        log.info(format("Test data size: {0}", testData));

        simpleTest(testData);
        complexTest(testData, false);
        complexTest(testData, true);
    }

    private void complexTest(Long testData, boolean worst) {
        String bestWorst = worst ? "Worst case scenario" : "Best case scenario";
        complexRepository.deleteAll();
        final LocalDate now = LocalDate.now();

        timeExecution(format("PerfDayComplex ({0}): Inserting test data", bestWorst), testData, () ->
                saveBatch(prepareComplex(now, worst, testData), complexRepository)
        );

        timeExecution(format("PerfDayComplex ({0}): Testing performance", bestWorst), testData, () -> {
                    final Iterable<PerfDayComplex> all = timeExecution(format("PerfDayComplex ({0}) > Repository: Testing performance", bestWorst), testData, (Supplier<List<PerfDayComplex>>) complexRepository::findAll);

                    timeExecution(format("PerfDayComplex ({0}) > Java: Testing performance", bestWorst), testData, () ->
                            all.forEach(p -> p.isWithinPeriod(now)));
                }
        );
    }

    private void simpleTest(Long testData) {
        simpleRepository.deleteAll();

        timeExecution("PerfDaySimple: Inserting test data", testData, () ->
                saveBatch(prepareSimple(testData), simpleRepository)
        );

        timeExecution("PerfDaySimple: Testing performance", testData, () -> {
            Iterable<PerfDaySimple> all1 = timeExecution("PerfDaySimple > Repository: Testing performance", testData, (Supplier<List<PerfDaySimple>>) simpleRepository::findAll);

            timeExecution("PerfDaySimple > Java: Testing performance", testData, () ->
                    all1.forEach(PerfDaySimple::getActive));
        });
    }

    private Stream<PerfDaySimple> prepareSimple(Long endExclusive) {
        return LongStream.range(0, endExclusive).boxed()
                .map(ii -> PerfDaySimple.builder().id(ii).active(true).customerNumber("").build());
    }

    private Stream<PerfDayComplex> prepareComplex(LocalDate now, boolean worst, Long endExclusive) {
        final LocalDate startDate = now.minusDays(2);
        final LocalDate endDate = now.minusDays(1);
        return LongStream.range(0, endExclusive).boxed()
                .map(ii -> {
                            final long l = endExclusive + 1;
                            return PerfDayComplex.builder()
                                    .id(ii)
                                    .periods(
                                            getDays(worst, startDate, endDate, ii, l)
                                    ).customerNumber("")
                                    .build();
                        }
                );
    }

    private Set<Period> getDays(boolean worst, LocalDate startDate, LocalDate endDate, Long ii, long l) {
        if (!worst) {
            return Collections.singleton(Period.builder().id(ii).startDate(null).endDate(null).build());
        }
        return Stream.of(
                Period.builder().id((l * 1) + ii).startDate(startDate).endDate(endDate).build(),
                Period.builder().id((l * 2) + ii).startDate(startDate).endDate(endDate).build(),
                Period.builder().id((l * 3) + ii).startDate(startDate).endDate(endDate).build()
        )
                .collect(Collectors.toSet());
    }

    private <T> void saveBatch(Stream<T> perfDayComplexStream, MongoRepository<T, Long> repository) {
        Iterators.partition(perfDayComplexStream.iterator(), 100)
                .forEachRemaining(repository::saveAll);
    }

    private void timeExecution(String name, Long testData, Runnable r) {
        timeExecution(name, testData, () -> {
            r.run();
            return null;
        });
    }

    private <T> T timeExecution(String name, Long testData, Supplier<T> r) {
        log.info(format("{0} ({1}) - starting", name, testData));
        long startTime = System.nanoTime();

        final T result = r.get();

        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        final double durationInSeconds = (double) duration / 1_000_000_000;
        final double val = durationInSeconds / testData;

        log.info(format("{0} ({2}) - Per element {1}s", name, BigDecimal.valueOf(val).toPlainString(), testData));
        log.info(format("{0} ({2}) - Elements per second: {1}", name, BigDecimal.valueOf(1 / val).toPlainString(), testData));
        log.info(format("{0} ({2}) - Total duration {1}s", name, durationInSeconds, testData));
        return result;
    }
}
