package eu.venthe.jpaexploration;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class JpaExplorationApplication {
    @Autowired RepositoriesPerformanceTest repositoriesPerformanceTest;

    public static void main(String[] args) {
        SpringApplication.run(JpaExplorationApplication.class, args);
    }
}
