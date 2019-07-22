package eu.venthe.gherkindemo;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GherkinDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(GherkinDemoApplication.class, args);
    }

    @Bean
    public ApplicationRunner runner(SampleService sampleService) {
        return arguments -> {
            sampleService.firstStep();
            sampleService.secondStep();
            sampleService.thirdStep();
        };
    }

}
