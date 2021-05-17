package eu.venthe.testcontainers;

import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Duration;
import java.util.function.BiFunction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.OK;

@Tag("IntegrationTest")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class TestcontainersApplicationTests {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:11")
            .withDatabaseName("prop")
            .withUsername("postgres")
            .withPassword("pass")
            .withExposedPorts(5432);

    @LocalServerPort
    int serverPort;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    MyEntityRepository myEntityRepository;

    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url",
                () -> String.format("jdbc:postgresql://localhost:%d/prop", postgres.getFirstMappedPort()));
        registry.add("spring.datasource.username", () -> postgres.getUsername());
        registry.add("spring.datasource.password", () -> postgres.getPassword());
        registry.add("spring.liquibase.enabled", () -> false);
        registry.add("spring.liquibase.dropFirst", () -> true);
        registry.add("spring.liquibase.enabled", () -> false);
        registry.add("spring.jpa.generate-ddl", () -> true);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
    }

    @Test
    void contextLoads() {
    }

    @Test
    void endpointCanBeCalled() {
        // given
        final MyEntity save = myEntityRepository.save(MyEntity.builder().value(1).build());

        // when
        final ResponseEntity<Integer> response = getRestTemplate().getForEntity(String.format("/value/%s", save.getId()), Integer.class);

        // then
        assertThat(response)
                .isNotNull()
                .is(_2xxSuccessful())
                .has(status(OK))
                .extracting(HttpEntity::getBody)
                .satisfies(value ->
                        assertThat(value)
                                .isEqualTo(save.getValue())
                );
    }

    private RestTemplate getRestTemplate() {
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(String.format("http://localhost:%d", serverPort)));
        return restTemplate;
    }

    private Condition<ResponseEntity<Integer>> status(HttpStatus status) {
        return new Condition<>(a -> a.getStatusCode().equals(status), "Status equality");
    }

    private Condition<ResponseEntity<Integer>> _2xxSuccessful() {
        return new Condition<>(a -> a.getStatusCode().is2xxSuccessful(), "Is 2xx successful");
    }

}
