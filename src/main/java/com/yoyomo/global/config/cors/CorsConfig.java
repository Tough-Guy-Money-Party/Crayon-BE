package com.yoyomo.global.config.cors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

	@Value("${crayon.monitor.host}")
	private String monitoringOrigin;

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
			.allowedOriginPatterns("http://localhost:3000",
				"https://test.api.crayon.land", "http://test.api.crayon.land",
				"https://test.crayon.land", "http://test.crayon.land",
				"https://*.crayon.land",
				"https://crayon.land", "http://crayon.land",
				"https://www.crayon.land", "http://www.crayon.land",
				monitoringOrigin
			)
			.allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
			.allowedHeaders("Authorization", "Content-Type")
			.allowCredentials(true)
			.maxAge(3600);
	}
}
