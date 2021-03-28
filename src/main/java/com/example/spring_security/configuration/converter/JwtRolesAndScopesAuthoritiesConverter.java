package com.example.spring_security.configuration.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Allows mapping of both SCOPE and ROLE from JWT token
 */
class JwtRolesAndScopesAuthoritiesConverter implements Converter<Jwt, Collection<GrantedAuthority>> {
    private static final String AUTHORITY_PREFIX = "ROLE_";
    private static final String AUTHORITY_ROLE_CLAIM_NAME = "roles";

    private final static Converter<Jwt, Collection<GrantedAuthority>> scopeConverter = new JwtGrantedAuthoritiesConverter();
    private final static Converter<Jwt, Collection<GrantedAuthority>> roleConverter;

    static {
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName(AUTHORITY_ROLE_CLAIM_NAME);
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix(AUTHORITY_PREFIX);
        roleConverter = jwtGrantedAuthoritiesConverter;
    }

    @Override
    public Collection<GrantedAuthority> convert(Jwt source) {
        return Stream.of(
                scopeConverter.convert(source),
                roleConverter.convert(source)
        )
                .filter(Objects::nonNull)
                .flatMap(Collection::stream)
                .collect(Collectors.toUnmodifiableList());
    }
}
