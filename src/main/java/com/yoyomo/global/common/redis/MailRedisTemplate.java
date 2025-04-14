package com.yoyomo.global.common.redis;

import com.yoyomo.domain.mail.domain.entity.LimitInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class MailRedisTemplate {

    private final RedisTemplate<String, String> rateLimitRedisTemplate;
    private final LimitInfo limitInfo;

    public String getTotalKey() {
        return limitInfo.getTotalKey();
    }

    public String getClubKey(UUID clubId) {
        return limitInfo.getClubKey(clubId);
    }

    public long getQuota(String key) {
        long max = limitInfo.getMaxByKey(key);
        String value = rateLimitRedisTemplate.opsForValue().get(key);
        if (value == null) {
            rateLimitRedisTemplate.opsForValue().set(key, String.valueOf(max), limitInfo.getTTL());
            return max;
        }
        return Long.parseLong(value);
    }

    public void decrement(String key, int requestSize) {
        rateLimitRedisTemplate.opsForValue().decrement(key, requestSize);
    }
}
