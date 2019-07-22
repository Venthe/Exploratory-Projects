package eu.venthe.hibernateinitializer;

import eu.venthe.hibernateinitializer.model.*;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;

import javax.transaction.Transactional;
import java.util.Collections;

@SpringBootApplication
@EntityScan("eu.venthe")
public class HibernateInitializerApplication {

    public static void main(String[] args) {
        SpringApplication.run(HibernateInitializerApplication.class, args);
    }

    @Bean
    ApplicationRunner applicationRunner(CustomerRepository customerRepository) {
        return args -> {
            Iterable<Customer> readCustomer = customerRepository.findAll();

            System.out.println();
        };
    }

}
