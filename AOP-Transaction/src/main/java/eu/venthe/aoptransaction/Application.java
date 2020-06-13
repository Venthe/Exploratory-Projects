package eu.venthe.aoptransaction;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.util.UUID;

@RequiredArgsConstructor
@SpringBootApplication
public class Application {
    private final TestTransactionService testTransactionService;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onStart() {
        testTransactionService.testTransactional("Test", UUID.randomUUID());
    }
}
