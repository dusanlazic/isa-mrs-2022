package com.team4.isamrs.controller;

import com.team4.isamrs.exception.UserNotFoundException;
import com.team4.isamrs.exception.error.ExceptionResponseBody;
import com.team4.isamrs.model.user.User;
import com.team4.isamrs.security.LoginRequest;
import com.team4.isamrs.security.TokenResponse;
import com.team4.isamrs.security.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = (User) authentication.getPrincipal();

        TokenResponse response = new TokenResponse(tokenUtils.generateAccessToken(user));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({ BadCredentialsException.class, InternalAuthenticationServiceException.class })
    public ExceptionResponseBody handleBadCredentials(Exception ex) {
        if (ex.getCause() instanceof UserNotFoundException)
            // Prevents the attackers from enumerating users by relying on the response time.
            passwordEncoder.encode("abc");

        return new ExceptionResponseBody(
                HttpStatus.UNAUTHORIZED.value(),
                "Bad credentials");
    }
}
