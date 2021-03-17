package com.example.spring_security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Controller
public class LoginController {

    private static final String INDEX_PAGE = "index";

    @GetMapping("/")
    public String index(Model model, UsernamePasswordAuthenticationToken principal) {
        model.addAttribute("userName", principal.getName());
        model.addAttribute("authorities", getAuthorities(principal));
        return INDEX_PAGE;
    }

    private List<String> getAuthorities(UsernamePasswordAuthenticationToken principal) {
        return principal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .map(this::simplifyUserRole)
                .collect(toList());
    }

    private String simplifyUserRole(String role) {
        return role.replaceAll("ROLE_", "");
    }
}
