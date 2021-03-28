package eu.venthe.togglz;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.togglz.core.Feature;
import org.togglz.core.manager.FeatureManager;

import java.util.stream.Collectors;

@Slf4j
@SpringBootApplication
public class TogglzApplication {

	public static void main(String[] args) {
		SpringApplication.run(TogglzApplication.class, args);
	}

	@Bean
	int test(FeatureManager featureManager) {
		log.info(featureManager.getFeatures().stream().map(Feature::name).collect(Collectors.joining(",")));
		return 0;
	}

}
