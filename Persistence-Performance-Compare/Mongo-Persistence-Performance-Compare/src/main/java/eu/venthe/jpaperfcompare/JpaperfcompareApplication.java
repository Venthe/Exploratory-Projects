package eu.venthe.jpaperfcompare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class JpaperfcompareApplication {
    public static void main(String[] args) {
        SpringApplication.run(JpaperfcompareApplication.class, args);
    }
}
