package com.example.spring_security.configuration.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

public class JwtRolesAndScopesAuthenticationConverter extends JwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {
    private JwtRolesAndScopesAuthenticationConverter() {
        setJwtGrantedAuthoritiesConverter(new JwtRolesAndScopesAuthoritiesConverter());
    }

    public static JwtRolesAndScopesAuthenticationConverter instance() {
        return new JwtRolesAndScopesAuthenticationConverter();
    }
}
