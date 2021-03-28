package eu.venthe.configuration_processor.configuration;

import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.DeprecatedConfigurationProperty;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * Example description for configuration. This will be reflected in metadata.
 */
@ConfigurationProperties("example")
@Configuration
@Data
@Slf4j
public class ExampleConfiguration {

    /**
     * Example description for text 1. This will be reflected in metadata.
     *
     * @deprecated Deprecation notice. This has to be duplicated in metadata.
     * <p>use {@link ExampleConfiguration#getText2()} instead.</p>
     */
    @Deprecated(
            since = "0.0.1"
    )
    @Getter(
            onMethod_ = @DeprecatedConfigurationProperty(
                    replacement = "example.text2",
                    reason = "Deprecation notice. This has to be duplicated"
            )
    )
    private String text1;
    private String text2;
    private Nested nested;

    @Slf4j
    @Data
    public static class Nested {
        private String text;
        /**
         * This has default value. Default value will be reflected in metadata.
         */
        private int number = 555;
    }

    @PostConstruct
    void onInit() {
        log.info("Loading configuration: Configuration={}, ConfigurationName{}", this, this.getClass().getSimpleName());
    }
}
