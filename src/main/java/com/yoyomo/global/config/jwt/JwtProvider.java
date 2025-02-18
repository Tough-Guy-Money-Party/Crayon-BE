package com.yoyomo.global.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.yoyomo.global.config.jwt.exception.InvalidTokenException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class JwtProvider {

    private static final String ACCESS_TOKEN_SUBJECT = "AccessToken";
    private static final String REFRESH_TOKEN_SUBJECT = "RefreshToken";
    private static final String ID_CLAIM = "id";
    private static final String BEARER = "Bearer ";

    public JwtProvider(@Value("${crayon.jwt.key}") String key,
                       @Value("${crayon.jwt.access.expiration}") Long accessTokenExpirationPeriod,
                       @Value("${crayon.jwt.refresh.expiration}") Long refreshTokenExpirationPeriod,
                       @Value("${crayon.jwt.access.header}") String accessHeader,
                       @Value("${crayon.jwt.refresh.header}") String refreshHeader,
                       @Value("${crayon.jwt.issuer}") String issuer
    ) {
        this.key = key;
        this.accessTokenExpirationPeriod = accessTokenExpirationPeriod;
        this.refreshTokenExpirationPeriod = refreshTokenExpirationPeriod;
        this.accessHeader = accessHeader;
        this.refreshHeader = refreshHeader;
        this.issuer = issuer;
        this.jwtVerifier = JWT.require(Algorithm.HMAC512(key))
                .withClaimPresence(ID_CLAIM)
                .withIssuer(issuer)
                .build();
    }

    private final String key;
    private final Long accessTokenExpirationPeriod;
    private final Long refreshTokenExpirationPeriod;
    private final String accessHeader;
    private final String refreshHeader;
    private final String issuer;
    private final JWTVerifier jwtVerifier;


    public String createAccessToken(Long id) {
        Date now = new Date();
        return JWT.create()
                .withSubject(ACCESS_TOKEN_SUBJECT)
                .withExpiresAt(new Date(now.getTime() + accessTokenExpirationPeriod))
                .withClaim(ID_CLAIM, id)
                .withIssuer(issuer)
                .sign(Algorithm.HMAC512(key));
    }

    public String createRefreshToken(Long id) {
        Date now = new Date();
        return JWT.create()
                .withSubject(REFRESH_TOKEN_SUBJECT)
                .withExpiresAt(new Date(now.getTime() + refreshTokenExpirationPeriod))
                .withClaim(ID_CLAIM, id)
                .sign(Algorithm.HMAC512(key));
    }

    public String extractRefreshToken(String token) {
        return token.replace(BEARER, "");
    }

    public String extractAccessToken(HttpServletRequest request) {
        String accessToken = request.getHeader(accessHeader);
        if (accessToken != null && accessToken.startsWith(BEARER)) {
            return accessToken.replace(BEARER, "");
        }
        throw new InvalidTokenException();
    }

    public Long extractId(String accessToken) {
        return validateToken(accessToken)
                .getClaim(ID_CLAIM)
                .asLong();
    }

    private DecodedJWT validateToken(String token) {
        try {
            return jwtVerifier.verify(token);
        } catch (JWTVerificationException e) {
            throw new InvalidTokenException(e);
        }
    }
}
