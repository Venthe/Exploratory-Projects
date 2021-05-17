package eu.venthe.camunda_demo;

import org.camunda.bpm.engine.ProcessEngine;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest(
        // ...other parameters...
        properties = {
                "camunda.bpm.generate-unique-process-engine-name=true",
                // this is only needed if a SpringBootProcessApplication
                // is used for the test
                "camunda.bpm.generate-unique-process-application-name=true",
                "spring.datasource.generate-unique-name=true",
                // additional properties...
        }
)
class WorkflowManagerTest {
    @Autowired
    ProcessEngine processEngine;

    @BeforeAll
    void beforeAll() {
//        init(processEngine);
    }

    @Test
    void test() {

    }
}