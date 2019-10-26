package eu.venthe.jpaexploration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@ComponentScan("eu.venthe.jpaexploration.*")
@Configuration
public class JpaExplorationApplication {

    public static void main(String[] args) {
        SpringApplication.run(JpaExplorationApplication.class, args);
    }

}
