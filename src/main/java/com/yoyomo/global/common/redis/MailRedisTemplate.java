package com.yoyomo.global.common.redis;

import java.util.UUID;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.yoyomo.domain.mail.domain.entity.LimitInfo;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MailRedisTemplate {

	private final RedisTemplate<String, Long> rateLimitRedisTemplate;
	private final LimitInfo limitInfo;

	public String getTotalKey() {
		return limitInfo.getTotalKey();
	}

	public String getClubKey(UUID clubId) {
		return limitInfo.getClubKey(clubId);
	}

	public long increment(String key, int requestSize) {
		return rateLimitRedisTemplate.opsForValue().increment(key, requestSize);
	}

	public long decrement(String key, int requestSize) {
		return rateLimitRedisTemplate.opsForValue().decrement(key, requestSize);
	}
}
