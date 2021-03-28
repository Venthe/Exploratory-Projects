package eu.venthe.configuration_processor.configuration;

import eu.venthe.configuration_processor.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ExampleConfigurationIntegrationTest extends BaseIntegrationTest {
    @Autowired
    private ExampleConfiguration configuration;

    @Test
    public void whenSimplePropertyQueriedThenReturnsPropertyValue() {
        assertThat(configuration.getText1()).isEqualTo("test.example.text1");
        assertThat(configuration.getText2()).isEqualTo("test.example.text2");
        assertThat(configuration.getNested().getNumber()).isEqualTo(321);
        assertThat(configuration.getNested().getText()).isEqualTo("test.example.nested.text");
    }
}
