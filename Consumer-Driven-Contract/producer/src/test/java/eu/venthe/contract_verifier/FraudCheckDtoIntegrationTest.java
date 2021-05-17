package eu.venthe.contract_verifier;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static java.math.BigDecimal.TEN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.of;

@SpringBootTest
class FraudCheckDtoIntegrationTest {
    public static final String TEST_ID = "testId";

    @Autowired
    ObjectMapper objectMapper;

    @ParameterizedTest
    @MethodSource("serializeDeserialize")
    void serializeDeserializeTest(Object o, Class<?> clazz) throws JsonProcessingException {
        // given
        var input = objectMapper.writeValueAsString(o);

        // when
        var deserialized = objectMapper.readValue(input, clazz);

        // then
        assertThat(deserialized).usingRecursiveComparison().isEqualTo(o);
    }

    static Stream<Arguments> serializeDeserialize() {
        return Stream.of(
                of(TEN, BigDecimal.class),
                of("Test", String.class),
                of(Map.of("t", 10), HashMap.class),
                of(Map.of("t", "Value"), HashMap.class)
        );
    }

    @Test
    void mapperTest() throws JsonProcessingException {
        objectMapper.setNodeFactory(JsonNodeFactory.withExactBigDecimals(true));

        // given
        var input = objectMapper
                .createObjectNode()
                .put("loanAmount", TEN)
                .put("client.id", TEST_ID)
                .set("client", objectMapper.createObjectNode()
                        .put("id", TEST_ID))
                .toPrettyString();

        // when
        var dto = objectMapper.readValue(input, FraudCheckDto.class);

        // then
        var softly = new SoftAssertions();

        softly.assertThat(dto).isNotNull();
        softly.assertThat(dto.getClientId())
                .isNotNull()
                .isEqualTo(TEST_ID);
        softly.assertThat(dto.getLoanAmount())
                .isNotNull()
                .isEqualTo(TEN);

        softly.assertThat(dto.getClient()).isNotNull();
        softly.assertThat(dto.getClient().getId())
                .isNotNull()
                .isEqualTo(TEST_ID);

        softly.assertAll();
    }

    @Test
    void mapperTest2() throws JsonProcessingException {
        // given
        var input = FraudCheckDto.builder()
                .loanAmount(BigDecimal.valueOf(131231.21))
                .client(new FraudCheckClientDto(TEST_ID))
                .build();
        var serialize = objectMapper.writeValueAsString(input);

        // when
        var dto = objectMapper.readValue(serialize, FraudCheckDto.class);

        // then
        assertThat(dto).isNotNull().isEqualTo(input);
    }

}