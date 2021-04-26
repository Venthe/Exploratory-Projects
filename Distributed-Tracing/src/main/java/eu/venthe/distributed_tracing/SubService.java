package eu.venthe.distributed_tracing;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.sleuth.annotation.NewSpan;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SubService {

    @NewSpan("subSpanInThread")
    public void test(String message) {
        log.info(message);
    }
}
