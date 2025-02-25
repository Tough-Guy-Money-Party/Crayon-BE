package com.yoyomo.global.config.logging;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import java.util.Set;

public class CustomRequestLoggingFilter extends CommonsRequestLoggingFilter {

    private static final Set<String> EXCLUSION_URI_PATTERNS = Set.of(
            "/swagger-ui",
            "/swagger-resources",
            "/v3/api-docs",
            "/health-check"
    );

    public CustomRequestLoggingFilter() {
        setIncludeQueryString(true);
        setIncludePayload(true);
        setMaxPayloadLength(10000);
        setAfterMessagePrefix("[request = ");
    }

    @Override
    protected boolean shouldLog(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return EXCLUSION_URI_PATTERNS.stream()
                .noneMatch(requestURI::startsWith);
    }

    @Override
    protected void beforeRequest(HttpServletRequest request, String message) {
    }

    @Override
    protected void afterRequest(HttpServletRequest request, String message) {
        logger.info(message);
    }
}
