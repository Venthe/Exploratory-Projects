package eu.venthe.distributed_tracing;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RequiredArgsConstructor
@Slf4j
@RestController
public class TestController {
    private final TestService testService;
    private final Tracer tracer;

    @GetMapping
    public String get() {
        log.info("Log success");
        testService.doWork(Integer.toString(new Random().nextInt()));
        return tracer.toString();
    }
}
