package com.example.spring_security;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.function.Consumer;

@SpringBootTest
public class SecurityPropertiesUnitTest {
    @Autowired
    SecurityProperties securityProperties;

    @Test
    void testDefaultUserConfiguration() {
        Assertions.assertThat(securityProperties.getUser())
                .satisfies(name("user"))
                .satisfies(password("password"))
                .satisfies(roles("developer"));
    }

    private static Consumer<SecurityProperties.User> roles(String... roles) {
        return user -> Assertions.assertThat(user.getRoles()).containsExactly(roles);
    }

    private static Consumer<SecurityProperties.User> password(String password) {
        return user -> Assertions.assertThat(user.getPassword()).isEqualTo(password);
    }

    private static Consumer<SecurityProperties.User> name(String name) {
        return user -> Assertions.assertThat(user.getName()).isEqualTo(name);
    }
}
