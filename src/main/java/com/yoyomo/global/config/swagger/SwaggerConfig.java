package com.yoyomo.global.config.swagger;

import org.springdoc.core.utils.SpringDocUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.yoyomo.global.common.annotation.CurrentUser;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerConfig {

	static {
		SpringDocUtils.getConfig().addAnnotationsToIgnore(CurrentUser.class);
	}

	@Bean
	public OpenAPI openApi() {
		String jwtSchemeName = "jwtAuth";
		SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwtSchemeName);
		Components components = new Components()
			.addSecuritySchemes(jwtSchemeName, new SecurityScheme()
				.name(jwtSchemeName)
				.type(SecurityScheme.Type.HTTP)
				.scheme("bearer")
				.bearerFormat("JWT"));

		return new OpenAPI()
			.addServersItem(new Server().url("/"))
			.addSecurityItem(securityRequirement)
			.components(components)
			.info(apiInfo());
	}

	private Info apiInfo() {
		return new Info()
			.title("핫가머파")
			.description("돈이 좋은 사람들의 API 문서입니다.")
			.version("1.1.0");
	}
}
