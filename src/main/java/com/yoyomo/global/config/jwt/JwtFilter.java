package com.yoyomo.global.config.jwt;

import com.yoyomo.domain.user.domain.entity.User;
import com.yoyomo.domain.user.domain.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private static final String EMPTY_ROLE = "EMPTY_ROLE";
    private static final String EMPTY_PASSWORD = "EMPTY_PASSWORD";

    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    private final GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
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
                                                FilterChain filterChain, String refreshToken)
            throws ServletException, IOException {
        log.info("checkAccessTokenAndRefreshToken() 호출");

        String validAccessToken = jwtProvider.extractAccessToken(request)
                .filter(jwtProvider::isTokenValid)
                .orElse(null);

        if (validAccessToken == null) {
            checkRefreshTokenAndReIssueAccessToken(response, refreshToken);
        } else {
            jwtProvider.extractEmail(validAccessToken)
                    .ifPresent(email -> userRepository.findByEmailAndDeletedAtIsNull(email)
                            .ifPresent(this::saveAuthentication));

            filterChain.doFilter(request, response);
        }
    }

    public void checkRefreshTokenAndReIssueAccessToken(HttpServletResponse response, String refreshToken) {
        userRepository.findByRefreshToken(refreshToken)
                .ifPresent(manager -> {
                    String reIssuedRefreshToken = reIssueRefreshToken(manager);
                    String accessToken = jwtProvider.createAccessToken(manager.getId(), manager.getEmail());
                    jwtProvider.sendAccessAndRefreshToken(response, accessToken, reIssuedRefreshToken);
                    jwtProvider.sendAccessToken(response, accessToken);
                });
    }

    @Transactional
    public String reIssueRefreshToken(User user) {
        String reIssuedRefreshToken = jwtProvider.createRefreshToken();
        user.updateRefreshToken(reIssuedRefreshToken);
        return reIssuedRefreshToken;
    }

    public void checkAccessTokenAndAuthentication(HttpServletRequest request, HttpServletResponse response,
                                                  FilterChain filterChain) throws ServletException, IOException {
        log.info("checkAccessTokenAndAuthentication() 호출");

        Optional<String> accessToken = jwtProvider.extractAccessToken(request)
                .filter(jwtProvider::isTokenValid);

        accessToken
                .flatMap(jwtProvider::extractEmail)
                .flatMap(userRepository::findByEmailAndDeletedAtIsNull)
                .ifPresent(this::saveAuthentication);

        filterChain.doFilter(request, response);
    }

    public void saveAuthentication(User myUser) {
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

}
