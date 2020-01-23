package eu.venthe.jpaperfcompare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableMongoRepositories
@SpringBootApplication
@EnableJpaAuditing
@EnableTransactionManagement
public class JpaperfcompareApplication {
    public static void main(String[] args) {
        SpringApplication.run(JpaperfcompareApplication.class, args);
    }
}
