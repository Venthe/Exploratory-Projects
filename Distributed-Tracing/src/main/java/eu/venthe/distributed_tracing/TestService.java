package eu.venthe.distributed_tracing;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.sleuth.annotation.NewSpan;
import org.springframework.cloud.sleuth.annotation.SpanTag;
import org.springframework.stereotype.Service;

import java.util.stream.IntStream;

@Slf4j
@AllArgsConstructor
@Service
public class TestService {
    private final SubService service;

    @SneakyThrows
    @NewSpan("Test")
    public void doWork(@SpanTag("customerId") String customerId) {
        log.info("Doing work " + customerId);
        IntStream.of(3).boxed()
                .map(Object::toString)
                .map(this::testWorkUnit)
                .forEach(Runnable::run);
        log.info("Work done.");
    }

    private Runnable testWorkUnit(String message) {
        return () -> service.test(message);
    }
}
