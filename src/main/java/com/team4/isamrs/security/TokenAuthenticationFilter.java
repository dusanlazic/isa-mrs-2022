package com.team4.isamrs.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.team4.isamrs.exception.TokenNotProvidedException;
import com.team4.isamrs.exception.UserNotFoundException;
import com.team4.isamrs.exception.error.ExceptionResponseBody;
import com.team4.isamrs.service.CustomUserDetailsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
        try {
            String accessToken = tokenUtils.getTokenFromRequest(request);
            DecodedJWT decodedJWT = tokenUtils.verifyToken(accessToken);
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(decodedJWT.getSubject());

            TokenBasedAuthentication authenticationToken = new TokenBasedAuthentication(userDetails, accessToken);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            filterChain.doFilter(request, response);
        } catch (TokenNotProvidedException | JWTVerificationException | UserNotFoundException ex) {
            sendResponse(response, HttpStatus.UNAUTHORIZED.value(), ex.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
            sendResponse(response, HttpStatus.INTERNAL_SERVER_ERROR.value(), "An error has occurred.");
        }
    }

    private void sendResponse(HttpServletResponse response, int status, String message) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(status);
        new ObjectMapper().writeValue(response.getOutputStream(), new ExceptionResponseBody(status, message));
    }
}
