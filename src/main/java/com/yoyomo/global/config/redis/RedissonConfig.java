package com.yoyomo.global.config.redis;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

@Configuration
public class RedissonConfig {

	private static final String REDISSON_HOST_FORMAT = "redis://%s:%d";

	@Value("${spring.data.redis.host}")
	private String host;

	@Value("${spring.data.redis.port}")
	private int port;

	@Value("${spring.data.redis.password}")
	private String password;

	@Bean
	public RedissonClient redissonClient() {
		Config config = new Config();
		if (StringUtils.hasText(password)) {
			config.useSingleServer().setAddress(String.format(REDISSON_HOST_FORMAT, host, port))
				.setPassword(password);
		} else {
			config.useSingleServer().setAddress(String.format(REDISSON_HOST_FORMAT, host, port));
		}

		return Redisson.create(config);
	}
}
