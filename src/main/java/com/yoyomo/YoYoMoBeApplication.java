package com.yoyomo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableJpaAuditing
@EnableMongoAuditing
@EnableWebSecurity
public class YoYoMoBeApplication {
	public static void main(String[] args) {
		SpringApplication.run(YoYoMoBeApplication.class, args);
	}
}
