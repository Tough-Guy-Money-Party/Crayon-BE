package com.yoyomo.global.common.redis;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

import com.yoyomo.domain.mail.domain.entity.LimitInfo;
import com.yoyomo.global.config.redis.LuaScriptConfig;
import com.yoyomo.global.config.redis.RedisConfig;
import com.yoyomo.global.config.redis.RedissonConfig;

/**
 * 1. ComponentScan은 @Bean 등록 X => @Import 사용
 * 2. @SpringBootTest X => yml/properties 자동 등록 X
 * 3. PropertySource -> yml 인식 X => .properties 사용
 */
@ComponentScan(basePackages = {"com.yoyomo.global.common.redis"})
@PropertySource("classpath:application-test.properties")
@Import({LimitInfo.class, RedisConfig.class, LuaScriptConfig.class, RedissonConfig.class})
@Configuration
public class TestConfig {
}


