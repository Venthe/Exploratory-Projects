package eu.venthe.aoptransaction;

import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@RequiredArgsConstructor
@Service
@Slf4j
@ToString
class Eventer {
    private final ApplicationEventPublisher applicationEventPublisher;

    void dispatch(String code) {
        registerSynchronizationAfterCommit(() -> {
            log.trace(code);
            applicationEventPublisher.publishEvent(code);
        });
    }

    private static void registerSynchronizationAfterCommit(Runnable runnable) {
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
            @Override
            public void afterCommit() {
                runnable.run();
            }
        });
    }
}
