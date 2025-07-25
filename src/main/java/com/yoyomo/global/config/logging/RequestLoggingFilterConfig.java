package com.yoyomo.global.config.logging;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class RequestLoggingFilterConfig {

	@Bean
	public CommonsRequestLoggingFilter logFilter() {
		return new CustomRequestLoggingFilter();
	}
}
