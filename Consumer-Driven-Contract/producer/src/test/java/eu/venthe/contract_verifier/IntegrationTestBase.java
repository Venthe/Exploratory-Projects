package eu.venthe.contract_verifier;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class IntegrationTestBase {
    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        RestAssuredMockMvc.standaloneSetup(new FraudController(objectMapper));
    }
}