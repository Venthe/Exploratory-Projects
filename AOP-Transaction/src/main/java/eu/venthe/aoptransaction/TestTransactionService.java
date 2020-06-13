package eu.venthe.aoptransaction;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class TestTransactionService {
    private final Eventer eventer;

    @LogExecutionTime("testTransactional")
    @LogStartStop
    @Transactional
    public void testTransactional(@Value("aa") String test,
                                  @Value("#{T(java.util.UUID).randomUUID()}") UUID uuid) {
        eventer.dispatch("Code");
        log.info("Did stuff");
        eventer.dispatch(test);
        log.info("Did stuff 2");
        eventer.dispatch(uuid.toString());
    }
}

