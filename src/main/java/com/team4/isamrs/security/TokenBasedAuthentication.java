package com.team4.isamrs.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

public class TokenBasedAuthentication extends AbstractAuthenticationToken {
    @Getter
    @Setter
    private String token;

    private final UserDetails principle;

    public TokenBasedAuthentication(UserDetails principle, String token) {
        super(principle.getAuthorities());
        this.principle = principle;
        this.token = token;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public String getCredentials() {
        return token;
    }

    @Override
    public UserDetails getPrincipal() {
        return principle;
    }
}
