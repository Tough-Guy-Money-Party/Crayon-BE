package com.yoyomo.global.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.yoyomo.global.config.jwt.exception.ExpiredTokenException;
import com.yoyomo.global.config.jwt.exception.InvalidTokenException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Slf4j
@Component
public class JwtProvider {

    private static final String ACCESS_TOKEN_SUBJECT = "AccessToken";
    private static final String REFRESH_TOKEN_SUBJECT = "RefreshToken";
    private static final String ID_CLAIM = "id";
    private static final String BEARER = "Bearer ";
    private static final int TOKEN_PREFIX_LENGTH = 7;

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
        this.jwtVerifier = JWT.require(Algorithm.HMAC512(key))
                .withClaimPresence(ID_CLAIM)
                .withIssuer(issuer)
                .build();
        this.jwtBuilder = JWT.create()
                .withIssuer(issuer);
    }

    private final String key;
    private final Long accessTokenExpirationPeriod;
    private final Long refreshTokenExpirationPeriod;
    private final String accessHeader;
    private final String refreshHeader;
    private final JWTVerifier jwtVerifier;
    private final JWTCreator.Builder jwtBuilder;

    public String createAccessToken(Long id) {
        return jwtBuilder
                .withClaim(ID_CLAIM, id)
                .withSubject(ACCESS_TOKEN_SUBJECT)
                .withExpiresAt(Instant.now().plusMillis(accessTokenExpirationPeriod))
                .sign(Algorithm.HMAC512(key));
    }

    public String createRefreshToken(Long id) {
        return jwtBuilder
                .withClaim(ID_CLAIM, id)
                .withSubject(REFRESH_TOKEN_SUBJECT)
                .withExpiresAt(Instant.now().plusMillis(refreshTokenExpirationPeriod))
                .sign(Algorithm.HMAC512(key));
    }

    public String extractRefreshToken(String token) {
        return token.substring(TOKEN_PREFIX_LENGTH);
    }

    public String extractAccessToken(HttpServletRequest request) {
        String accessToken = request.getHeader(accessHeader);
        if (accessToken != null && accessToken.startsWith(BEARER)) {
            return accessToken.substring(TOKEN_PREFIX_LENGTH);
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
        } catch (TokenExpiredException e) {
            throw new ExpiredTokenException();
        } catch (JWTVerificationException e) {
            throw new InvalidTokenException(e);
        }
    }
}
