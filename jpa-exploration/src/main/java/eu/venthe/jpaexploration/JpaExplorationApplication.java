package eu.venthe.jpaexploration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class JpaExplorationApplication {
    @Autowired
    RepositoriesPerformanceTest repositoriesPerformanceTest;

    public static void main(String[] args) {
        SpringApplication.run(JpaExplorationApplication.class, args);
    }
}
