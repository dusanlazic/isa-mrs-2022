package com.team4.isamrs.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.team4.isamrs.service.CustomUserDetailsService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private TokenUtils tokenUtils;

    private CustomUserDetailsService customUserDetailsService;

    public TokenAuthenticationFilter(TokenUtils tokenUtils, CustomUserDetailsService customUserDetailsService) {
        this.tokenUtils = tokenUtils;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = tokenUtils.getTokenFromRequest(request);
        DecodedJWT decodedJWT = tokenUtils.verifyToken(accessToken);
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(decodedJWT.getSubject());

        TokenBasedAuthentication authenticationToken = new TokenBasedAuthentication(userDetails, accessToken);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request, response);
    }
}
