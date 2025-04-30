package com.yoyomo.global.common.redis;

import com.yoyomo.domain.mail.domain.entity.LimitInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class MailLimiterWithLock implements MailLimiter {

    private final RedissonClient redissonClient;
    private final MailRedisTemplate mailRedisTemplate;
    private final LimitInfo limitInfo;

    /**
     * 한도: 누적 사용량 관리
     *
     * @param clubId
     * @param requestSize
     * @return
     */

    @Override
    public boolean tryConsume(UUID clubId, int requestSize) {
        String totalKey = mailRedisTemplate.getTotalKey();
        String clubKey = mailRedisTemplate.getClubKey(clubId);

        RLock totalLock = redissonClient.getLock(totalKey);
        RLock clubLock = redissonClient.getLock(clubKey);
        RLock multiLock = redissonClient.getMultiLock(totalLock, clubLock);
        boolean locked = false;
        try {
            locked = multiLock.tryLock(1, 3, TimeUnit.SECONDS);
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
        long total = mailRedisTemplate.increment(totalKey, requestSize);
        long club = mailRedisTemplate.increment(clubKey, requestSize);
        if (total <= limitInfo.getMaxByKey(totalKey) && club <= limitInfo.getMaxByKey(clubKey)) {
            return true;
        }

        rollback(requestSize, totalKey, clubKey);
        return false;
    }

    private void rollback(int requestSize, String totalKey, String clubKey) {
        mailRedisTemplate.decrement(totalKey, requestSize);
        mailRedisTemplate.decrement(clubKey, requestSize);
    }
}
