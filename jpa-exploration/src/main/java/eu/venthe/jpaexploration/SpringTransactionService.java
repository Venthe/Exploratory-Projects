package eu.venthe.jpaexploration;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.Callable;

@Service
public class SpringTransactionService {

    @Transactional
    public <T> T executeInTransaction(Callable<T> callable) throws Exception {
        return callable.call();
    }

    @Transactional
    public void executeInTransaction(Runnable runnable) {
        runnable.run();
    }
}
