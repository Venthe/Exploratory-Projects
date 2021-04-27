package eu.venthe.micrometer;

import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

@RestController
@RequiredArgsConstructor
@Slf4j
public class SampleService {
    private final MeterRegistry meterRegistry;
    private static String[] VALUES = {"A", "B", "C"};
    private final Map<String, Timer> timerMap = new HashMap<>();

    @GetMapping
    @Timed(value = "calculate_all", longTask = true, histogram = true, percentiles = {0.1, 0.5, 0.9})
    public void calculate() {
        IntStream.range(0, 100)
                .forEach(i -> Arrays.stream(VALUES)
                        .forEach(value -> getTimer(value).record(task())));
    }

    private Runnable task() {
        return () -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
    }

    private Timer getTimer(String tag) {
        timerMap.putIfAbsent(
                tag,
                Timer.builder("calculate")
                        .publishPercentileHistogram()
                        .publishPercentiles(0.1, 0.5, 0.9)
                        .tag("string", tag)
                        .register(meterRegistry)
        );
        return timerMap.get(tag);
    }
}
