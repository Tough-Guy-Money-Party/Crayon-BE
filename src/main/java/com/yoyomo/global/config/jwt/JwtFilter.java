package com.yoyomo.global.config.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yoyomo.domain.user.domain.entity.Manager;
import com.yoyomo.domain.user.domain.repository.ManagerRepository;
import com.yoyomo.global.common.dto.ResponseDto;
import com.yoyomo.global.config.jwt.exception.InvalidTokenException;
import com.yoyomo.global.config.jwt.presentation.constant.JwtError;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

import static jakarta.servlet.http.HttpServletResponse.SC_OK;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;
    private final ManagerRepository managerRepository;
    private final GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();
    private final String EMPTY_ROLE = "EMPTY_ROLE";
    private final String EMPTY_PASSWORD = "EMPTY_PASSWORD";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String refreshToken = jwtProvider.extractRefreshToken(request)
                .filter(jwtProvider::isTokenValid)
                .orElse(null);

        if (refreshToken != null) {
            checkAccessTokenAndRefreshToken(request, response, filterChain, refreshToken);
            return;
        }
        checkAccessTokenAndAuthentication(request, response, filterChain);
    }

    public void checkAccessTokenAndRefreshToken(HttpServletRequest request, HttpServletResponse response,
                                                FilterChain filterChain, String refreshToken) throws ServletException, IOException {
        log.info("checkAccessTokenAndRefreshToken() 호출");

        String validAccessToken = jwtProvider.extractAccessToken(request)
                .filter(jwtProvider::isTokenValid)
                .orElse(null);

        if (validAccessToken == null) {
            checkRefreshTokenAndReIssueAccessToken(response, refreshToken);
        } else {
            jwtProvider.extractEmail(validAccessToken)
                    .ifPresent(email -> managerRepository.findByEmailAndDeletedAtIsNull(email)
                            .ifPresent(this::saveAuthentication));

            filterChain.doFilter(request, response);
        }
    }

    public void checkRefreshTokenAndReIssueAccessToken(HttpServletResponse response, String refreshToken) {
        managerRepository.findByRefreshToken(refreshToken)
                .ifPresent(manager -> {
                    String reIssuedRefreshToken = reIssueRefreshToken(manager);
                    String accessToken = jwtProvider.createAccessToken(manager.getId(), manager.getEmail());
                    jwtProvider.sendAccessAndRefreshToken(response, accessToken, reIssuedRefreshToken);
                    jwtProvider.sendAccessToken(response, accessToken);
                });
    }

    @Transactional
    public String reIssueRefreshToken(Manager manager) {
        String reIssuedRefreshToken = jwtProvider.createRefreshToken();
        manager.updateRefreshToken(reIssuedRefreshToken);
        return reIssuedRefreshToken;
    }

    public void checkAccessTokenAndAuthentication(HttpServletRequest request, HttpServletResponse response,
                                                  FilterChain filterChain) throws ServletException, IOException {
        log.info("checkAccessTokenAndAuthentication() 호출");

        Optional<String> accessToken = jwtProvider.extractAccessToken(request)
                .filter(jwtProvider::isTokenValid);

        /*
        변경된 로직: accessToken이 비어있다는 것은 isTokenValid의 유효성 검사를 통과하지 못했다는 뜻이기 때문에 바로 예외처리 진행
        토큰이 없거나 / 유효하지 않다는 뜻이기 때문에 invalid로 처리
        CustomAuthenticationEntryPoint에서 처리
         */
        if (accessToken.isEmpty()) {
            request.setAttribute("exception", JwtError.INVALID_TOKEN.getCode());
            return;
        }

        accessToken
                .flatMap(jwtProvider::extractEmail)
                .flatMap(managerRepository::findByEmailAndDeletedAtIsNull)
                .ifPresent(this::saveAuthentication);

        filterChain.doFilter(request, response);
    }

    public void saveAuthentication(Manager myUser) {
        UserDetails userDetailsUser = org.springframework.security.core.userdetails.User.builder()
                .username(myUser.getEmail())
                .password(EMPTY_PASSWORD)
                .roles(EMPTY_ROLE)
                .build();

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userDetailsUser, null,
                        authoritiesMapper.mapAuthorities(userDetailsUser.getAuthorities()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public void jwtExceptionHandler(HttpServletResponse response) {
        response.setStatus(SC_OK);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            InvalidTokenException error = new InvalidTokenException();
            String json = new ObjectMapper().writeValueAsString(ResponseDto.of(error.getErrorCode(), error.getMessage()));
            response.getWriter().write(json);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}