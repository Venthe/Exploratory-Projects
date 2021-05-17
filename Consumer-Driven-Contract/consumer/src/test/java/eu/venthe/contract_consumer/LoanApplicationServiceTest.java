package eu.venthe.contract_consumer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigureStubRunner(ids = {"eu.venthe:ContractVerifier:+:stubs:6565"},
        stubsMode = StubRunnerProperties.StubsMode.LOCAL)
public class LoanApplicationServiceTest {
    @Test
    void test() throws URISyntaxException {
        ResponseEntity<ObjectNode> loanAmount = new RestTemplate().exchange(new URI("http://localhost:6565/fraudcheck"),
                HttpMethod.PUT,
                new HttpEntity<>(Map.of("client.id", 1234567890, "loanAmount", 99999)),
                ObjectNode.class);

        assertThat(loanAmount).isNotNull();
        assertThat(loanAmount.getStatusCode()).isNotNull().isEqualTo(HttpStatus.OK);
        assertThat(loanAmount.getBody()).isNotNull().satisfies(body -> {
            ObjectNode body1 = (ObjectNode) body;
            assertThat(body1.get("fraudCheckStatus").asText()).isEqualTo("FRAUD");
            assertThat(body1.get("rejection.reason").asText()).isEqualTo("Amount too high");
        });
    }
}