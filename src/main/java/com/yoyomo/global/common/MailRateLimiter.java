package com.yoyomo.global.common;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class MailRateLimiter {

    private static final String TOTAL_KEY = "global:email:total";
    private static final String CLUB_KEY = "global:email:";
    private static final Map<String, Long> LIMIT = Map.of(
            TOTAL_KEY, 50_000L,
            CLUB_KEY, 300L
    );
    private static final Duration TTL = Duration.ofHours(24);

    private final RedisTemplate<String, String> rateLimitRedisTemplate;

    public boolean isRateLimited(UUID clubId, int requestSize) {
        String clubKey = CLUB_KEY + clubId;

        if (exceededLimit(requestSize, clubKey)) {
            return true;
        }

        rateLimitRedisTemplate.opsForValue().decrement(TOTAL_KEY, requestSize);
        rateLimitRedisTemplate.opsForValue().decrement(clubKey, requestSize);
        return false;
    }

    private boolean exceededLimit(int requestSize, String clubKey) {
        long totalRemaining = getRemaining(TOTAL_KEY, LIMIT.get(TOTAL_KEY));
        long clubRemaining = getRemaining(clubKey, LIMIT.get(CLUB_KEY));

        return totalRemaining < requestSize || clubRemaining < requestSize;
    }

    private long getRemaining(String key, long amount) {
        String value = rateLimitRedisTemplate.opsForValue().get(key);
        if (value != null) {
            return Long.parseLong(value);
        }
        rateLimitRedisTemplate.opsForValue().set(key, String.valueOf(amount), TTL);
        return amount;
    }
}
