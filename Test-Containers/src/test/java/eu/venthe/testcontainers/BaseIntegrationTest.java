package eu.venthe.testcontainers;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@DisplayName("During startup")
public class BaseIntegrationTest extends AbstractIntegrationTest {

    @Nested
    @DisplayName("after context is loaded")
    class OnContextLoad {

        @Test
        @DisplayName("it should start application")
        void applicationStarts() {
            // This test checks if application can be started at all
        }

        @Test
        @DisplayName("it should run all tests in transaction")
        public void testIsRunInTransaction() {
            Assertions.assertThat(TransactionSynchronizationManager.isActualTransactionActive())
                    .isTrue();
        }
    }
}
