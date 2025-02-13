package com.yoyomo.global.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.yoyomo.global.config.jwt.exception.ExpiredTokenException;
import com.yoyomo.global.config.jwt.exception.InvalidTokenException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Getter
@Component
@RequiredArgsConstructor
public class JwtProvider {
    @Value("${crayon.jwt.key}")
    private String key;

    @Value("${crayon.jwt.access.expiration}")
    private Long accessTokenExpirationPeriod;

    @Value("${crayon.jwt.refresh.expiration}")
    private Long refreshTokenExpirationPeriod;

    @Value("${crayon.jwt.access.header}")
    private String accessHeader;

    @Value("${crayon.jwt.refresh.header}")
    private String refreshHeader;

    private static final String ACCESS_TOKEN_SUBJECT = "AccessToken";
    private static final String REFRESH_TOKEN_SUBJECT = "RefreshToken";
    private static final String ID_CLAIM = "id";
    private static final String BEARER = "Bearer ";

    public String createAccessToken(Long id) {
        Date now = new Date();
        return JWT.create()
                .withSubject(ACCESS_TOKEN_SUBJECT)
                .withExpiresAt(new Date(now.getTime() + accessTokenExpirationPeriod))
                .withClaim(ID_CLAIM, id)
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
        try {
            return JWT.require(Algorithm.HMAC512(key))
                    .build()
                    .verify(accessToken)
                    .getClaim(ID_CLAIM)
                    .asLong();
        } catch (Exception e) {
            throw new InvalidTokenException();
        }
    }

    public void validateToken(String token) {
        try {
            JWT.require(Algorithm.HMAC512(key)).build().verify(token);
        } catch (TokenExpiredException e) {
            throw new ExpiredTokenException();
        } catch (Exception e) {
            throw new InvalidTokenException();
        }
    }
}
