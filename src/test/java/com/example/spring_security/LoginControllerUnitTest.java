package com.example.spring_security;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerUnitTest {

    @Value("${spring.security.user.name}")
    String user;
    @Value("${spring.security.user.password}")
    String password;

    @Autowired
    MockMvc mvc;

    @SneakyThrows
    @Test
    void GET_login() {
        mvc.perform(get("/login"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content()
                        .string(containsString("<form class=\"form-signin\" method=\"post\" action=\"/login\">"))
                );
    }

    @SneakyThrows
    @Test
    void POST_login() {
        mvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("username", user)
                .param("password", password)
                .with(csrf())
        )
                .andDo(print())
                .andExpect(redirectedUrl("/"))
                .andExpect(status().isFound());
    }

    @SneakyThrows
    @Test
    @WithMockUser()
    void GET_index() {
        mvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content()
                        .string(containsString("You are successfully logged in <span style=\"font-weight:bold\">user</span>"))
                )
                .andExpect(content()
                        .string(containsString("<li>USER</li>"))
                )
                .andExpect(content()
                        .string(containsString("<div class=\"col\"><h1>Login with Spring Security</h1></div>"))
                );
    }

    @SneakyThrows
    @Test
    void GET_logout() {
        mvc.perform(get("/logout"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content()
                        .string(containsString("<form class=\"form-signin\" method=\"post\" action=\"/logout\">"))
                );
    }

    @SneakyThrows
    @Test
    @WithMockUser()
    void POST_logout() {
        mvc.perform(post("/logout")
                .with(csrf())
        )
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}
