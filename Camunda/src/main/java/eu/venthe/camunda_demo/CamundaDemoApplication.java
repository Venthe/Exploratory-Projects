package eu.venthe.camunda_demo;

import org.camunda.bpm.application.ProcessApplication;
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableProcessApplication("myProcessApplicationName")
@SpringBootApplication
public class CamundaDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(CamundaDemoApplication.class, args);
    }

}
