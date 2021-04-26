package eu.venthe.distributed_tracing;

import brave.baggage.CorrelationScopeCustomizer;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static brave.baggage.BaggageFields.PARENT_ID;
import static brave.baggage.CorrelationScopeConfig.SingleCorrelationField;

@SpringBootApplication
@RequiredArgsConstructor
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    // Add parent ID as baggage
    @Bean
    public CorrelationScopeCustomizer addParentSpanId() {
        return builder -> builder
                .add(SingleCorrelationField.newBuilder(PARENT_ID).build());
    }
}
