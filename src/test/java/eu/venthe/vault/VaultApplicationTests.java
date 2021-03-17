package eu.venthe.vault;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class VaultApplicationTests {

	@Value("${from-vault}")
	int basic;

	@Value("${from-vault-2}")
	int nested;

	@Test
	void contextLoads() {
	}

	@Test
	void vaultProvidesData() {
		Assertions.assertThat(basic).isEqualTo(1);
		Assertions.assertThat(nested).isEqualTo(3);
	}
}
