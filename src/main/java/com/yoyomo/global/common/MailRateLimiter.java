package com.yoyomo.global.common;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class MailRateLimiter {

    private static final Duration TTL = Duration.ofHours(24);

    private final RedisTemplate<String, String> redisTemplate;

    public boolean isRateLimited(UUID clubId, int requestSize) {
        String totalKey = Limit.TOTAL.key;
        String clubKey = Limit.CLUB.key(clubId);

        Long totalRemaining = consume(totalKey, Limit.TOTAL.max, requestSize);
        Long clubRemaining = consume(clubKey, Limit.CLUB.max, requestSize);

        if (totalRemaining < 0 || clubRemaining < 0) {
            rollback(totalKey, requestSize);
            rollback(clubKey, requestSize);
            return true;
        }

        return false;
    }

    private Long consume(String key, long limit, int requestSize) {
        long initialValue = limit - requestSize;

        if (limit < requestSize) {
            return -1L;
        }

        Boolean isNew = redisTemplate.opsForValue().setIfAbsent(key, String.valueOf(initialValue), TTL);
        if (Boolean.TRUE.equals(isNew)) {
            return initialValue;
        }

        return redisTemplate.opsForValue().decrement(key, requestSize);
    }

    private void rollback(String key, int requestSize) {
        redisTemplate.opsForValue().increment(key, requestSize);
    }

    @AllArgsConstructor
    enum Limit {
        TOTAL("global:email:total", 50_000),
        CLUB("global:email:", 300);

        private final String key;
        private final long max;

        public String key(UUID clubId) {
            return key + clubId;
        }
    }
}
