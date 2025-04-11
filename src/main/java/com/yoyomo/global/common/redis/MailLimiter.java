package com.yoyomo.global.common.redis;

import lombok.RequiredArgsConstructor;
import org.redisson.RedissonMultiLock;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class MailLimiter {

    private static final String LOCK_PREFIX = "lock:";
    private static final String TOTAL_ID = "total";

    private final RedissonClient redissonClient;
    private final MailRedisTemplate mailRedisTemplate;

    public boolean tryConsume(UUID clubId, int requestSize) {
        String totalKey = mailRedisTemplate.getTotalKey();
        String clubKey = mailRedisTemplate.getClubKey(clubId);

        RLock totalLock = redissonClient.getLock(LOCK_PREFIX + TOTAL_ID);
        RLock clubLock = redissonClient.getLock(LOCK_PREFIX + clubId);
        RLock multiLock = new RedissonMultiLock(totalLock, clubLock);

        boolean locked = false;
        try {
            locked = multiLock.tryLock(3, 5, TimeUnit.SECONDS);
            if (!locked) {
                return false;
            }
            return checkLimit(requestSize, totalKey, clubKey);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        } finally {
            if (locked) {
                multiLock.unlock();
            }
        }
    }

    private boolean checkLimit(int requestSize, String totalKey, String clubKey) {
        long total = mailRedisTemplate.getQuota(totalKey);
        long club = mailRedisTemplate.getQuota(clubKey);

        if (total >= requestSize && club >= requestSize) {
            mailRedisTemplate.decrement(totalKey, requestSize);
            mailRedisTemplate.decrement(clubKey, requestSize);
            return true;
        }
        return false;
    }
}
