package com.yoyomo.global.config.logging;

import java.util.Set;

import org.springframework.web.filter.CommonsRequestLoggingFilter;

import jakarta.servlet.http.HttpServletRequest;

public class CustomRequestLoggingFilter extends CommonsRequestLoggingFilter {

	private static final Set<String> EXCLUSION_URI_PATTERNS = Set.of(
		"/swagger-ui",
		"/swagger-resources",
		"/v3/api-docs",
		"/health-check",
		"/actuator/prometheus"
	);

	public CustomRequestLoggingFilter() {
		setIncludeQueryString(true);
		setIncludePayload(true);
		setMaxPayloadLength(10000);
		setAfterMessagePrefix("[request = ");
	}

	@Override
	protected boolean shouldLog(HttpServletRequest request) {
		String requestUri = request.getRequestURI();
		return EXCLUSION_URI_PATTERNS.stream()
			.noneMatch(requestUri::startsWith);
	}

	@Override
	protected void beforeRequest(HttpServletRequest request, String message) {
	}

	@Override
	protected void afterRequest(HttpServletRequest request, String message) {
		logger.info(message);
	}
}
