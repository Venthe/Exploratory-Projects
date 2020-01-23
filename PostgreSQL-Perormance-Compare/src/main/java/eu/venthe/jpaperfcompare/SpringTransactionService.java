package eu.venthe.jpaperfcompare;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SpringTransactionService {
    @Transactional(readOnly = true)
    public void executeInTransaction(Runnable callable) {
        callable.run();
    }
}
