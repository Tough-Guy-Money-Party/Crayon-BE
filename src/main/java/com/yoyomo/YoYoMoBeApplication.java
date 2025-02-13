package com.yoyomo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaAuditing
@EnableMongoAuditing
@EnableScheduling
public class YoYoMoBeApplication {
    public static void main(String[] args) {
        SpringApplication.run(YoYoMoBeApplication.class, args);
    }
}
