package com.yoyomo.global.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yoyomo.global.common.dto.ResponseDto;
import com.yoyomo.global.config.jwt.presentation.constant.JwtError;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Slf4j
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        Integer exception = (Integer)request.getAttribute("exception");

        //401
        if (exception.equals(JwtError.INVALID_TOKEN.getCode())) {
            log.error("토큰 유효하지 않음");
            setResponse(response, JwtError.INVALID_TOKEN);
        }
        //401
        else if (exception.equals(JwtError.EXPIRED_TOKEN.getCode())) {
            log.error("토큰 만료됨");
            setResponse(response, JwtError.EXPIRED_TOKEN);
        }
    }

    private void setResponse(HttpServletResponse response, JwtError jwtError) throws IOException {
        response.setStatus(jwtError.getCode());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String json = new ObjectMapper().writeValueAsString(ResponseDto.of(jwtError.getCode(), jwtError.getMessage()));
        response.getWriter().write(json);
    }
}

