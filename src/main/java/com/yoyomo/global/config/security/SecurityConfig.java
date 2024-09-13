package com.yoyomo.global.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yoyomo.domain.user.domain.repository.ManagerRepository;
import com.yoyomo.global.common.dto.ResponseDto;
import com.yoyomo.global.config.jwt.JwtFilter;
import com.yoyomo.global.config.jwt.JwtProvider;
import com.yoyomo.global.config.jwt.presentation.constant.ResponseMessage;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtProvider jwtProvider;
    private final ManagerRepository managerRepository;

    @Bean
    public SecurityFilterChain config(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(corsConfigurer ->
                        corsConfigurer.configurationSource(source())
                )
                .httpBasic(AbstractHttpConfigurer::disable)
                .headers((headers) -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));
        http
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable);

        http
                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**").permitAll()
                                .requestMatchers("/applications").permitAll()
                                .requestMatchers("/results/{clubId}/details").permitAll()
                                .requestMatchers("/user/login/**").permitAll()
                                .anyRequest().permitAll());

        http
                .addFilterBefore(jwtAuthenticationProcessingFilter(), AbstractPreAuthenticatedProcessingFilter.class)
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling
                                .authenticationEntryPoint(customAuthenticationEntryPoint())
                );
        return http.build();
    }

    @Bean
    public CorsConfigurationSource source() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addExposedHeader("Authorization");
        configuration.addAllowedOriginPattern("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    // 인증정보 없이 요청시 예외처리
    @Bean
    public AuthenticationEntryPoint customAuthenticationEntryPoint() {
        return (request, response, authException) -> {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            String json = new ObjectMapper().writeValueAsString(ResponseDto.of(401, ResponseMessage.INVALID_TOKEN.getMessage()));
            response.getWriter().write(json);
        };
    }

    public JwtFilter jwtAuthenticationProcessingFilter() {
        return new JwtFilter(jwtProvider, managerRepository);
    }
}
