package com.team4.isamrs.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.team4.isamrs.exception.TokenNotProvidedException;
import com.team4.isamrs.model.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class TokenUtils {

    private final String issuer;
    private final Integer lifespan;
    private final Algorithm signatureAlgorithm;
    private final JWTVerifier verifier;

    public TokenUtils(@Value("${token-utils.secret}") String secret,
                      @Value("${token-utils.issuer}") String issuer,
                      @Value("${token-utils.lifespan}") Integer lifespan) {
        this.issuer = issuer;
        this.lifespan = lifespan;
        this.signatureAlgorithm = Algorithm.HMAC256(secret.getBytes());
        this.verifier = JWT.require(signatureAlgorithm).build();
    }

    public String generateAccessToken(User user) {
        return JWT.create()
                .withSubject(user.getUsername())
                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .withIssuedAt(Date.from(ZonedDateTime.now().toInstant()))
                .withExpiresAt(Date.from(ZonedDateTime.now().plusMinutes(lifespan).toInstant()))
                .withIssuer(issuer)
                .sign(signatureAlgorithm);
    }

    public String getTokenFromRequest(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7); // Ignore "Bearer " part
        }
        throw new TokenNotProvidedException("Token is not provided."); // Cannot be thrown since filter is not in
    }

    public boolean tokenIsPresentInRequest(HttpServletRequest request) {
        return request.getHeader("Authorization") != null;
    }

    public String generateConfirmationToken(User user) {
        return JWT.create()
                .withSubject(user.getUsername())
                .withIssuedAt(Date.from(ZonedDateTime.now().toInstant()))
                .withExpiresAt(Date.from(ZonedDateTime.now().plusMinutes(15).toInstant()))
                .withIssuer(issuer)
                .sign(signatureAlgorithm);
    }

    public DecodedJWT verifyToken(String token) {
        return verifier.verify(token);
    }
}
