package com.yoyomo.global.common;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class MailRateLimiter {

    private static final String REMAINING_KEY = "global:email";
    private static final long MAX_EMAILS_PER_DAY = 50_000;
    private static final Duration TTL = Duration.ofHours(24);

    private final RedisTemplate<String, String> rateLimitRedisTemplate;

    public boolean isRateLimited(int requestSize) {
        ValueOperations<String, String> ops = rateLimitRedisTemplate.opsForValue();

        long remaining = getRemaining(ops);
        if (remaining < requestSize) {
            return true;
        }

        ops.decrement(REMAINING_KEY, requestSize);
        return false;
    }

    private long getRemaining(ValueOperations<String, String> ops) {
        String remaining = ops.get(REMAINING_KEY);
        if (remaining == null) {
            ops.set(REMAINING_KEY, String.valueOf(MAX_EMAILS_PER_DAY), TTL);
            return MAX_EMAILS_PER_DAY;
        }
        return Long.parseLong(remaining);
    }
}
