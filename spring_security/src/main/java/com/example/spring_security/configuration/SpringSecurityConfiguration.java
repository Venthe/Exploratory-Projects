package com.example.spring_security.configuration;

import com.example.spring_security.configuration.converter.JwtRolesAndScopesAuthenticationConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
// To allow 'hasAuthority' in @PreAuthorize in SPeL
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(expressionInterceptUrlRegistry -> expressionInterceptUrlRegistry
                        // .antMatchers(HttpMethod.GET, "/foos/**").hasAuthority("SCOPE_read")
                        // .antMatchers(HttpMethod.POST, "/foos").hasAuthority("SCOPE_write")
                        .anyRequest()
                        .authenticated()
                        .and()
                )
                .oauth2ResourceServer(httpSecurityOAuth2ResourceServerConfigurer -> httpSecurityOAuth2ResourceServerConfigurer
                        .jwt()
                        .jwtAuthenticationConverter(JwtRolesAndScopesAuthenticationConverter.instance())
                )
                .csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                )
                .headers(httpSecurityHeadersConfigurer ->
                        httpSecurityHeadersConfigurer.contentSecurityPolicy(
                                "default-src 'self'; " +
                                        "child-src 'none';" +
                                        "script-src 'self' https://trustedscripts.example.com;" +
                                        "object-src https://trustedplugins.example.com;" +
                                        "report-uri /csp-report-endpoint/;" +
                                        "img-src https://*;"
                        )
                );
    }
}
