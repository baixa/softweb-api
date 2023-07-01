package com.softweb.api.store.services;

import com.softweb.api.store.model.entities.Authorities;
import com.softweb.api.store.model.entities.User;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {
    private Authentication authentication;
    private final UserService userService;

    public AuthenticationService(UserService userService) {
        this.userService = userService;
    }

    private void loadAuthentication() {
        authentication = SecurityContextHolder.getContext().getAuthentication();
    }

    public Authorities getAuthenticationAuthority() {
        loadAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken)
            return null;

        GrantedAuthority authority = authentication.getAuthorities().stream().findFirst().orElse(null);
        return authority == null ? null : Authorities.valueOf(authority.getAuthority());
    }

    public String getAuthenticationName() {
        loadAuthentication();
        return authentication instanceof  AnonymousAuthenticationToken ?
                null : authentication.getName();
    }

    public Optional<User> getAuthenticatedUser() {
        return userService.getUserByUsername(getAuthenticationName());
    }
}
