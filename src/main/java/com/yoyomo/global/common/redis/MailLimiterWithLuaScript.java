package com.yoyomo.global.common.redis;

import com.yoyomo.domain.mail.domain.entity.LimitInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class MailLimiterWithLuaScript implements MailLimiter {

    private final RedisTemplate<String, Long> rateLimitRedisTemplate;
    private final RedisScript<Boolean> mailLimitScript;
    private final LimitInfo limitInfo;

    /**
     * 한도: 사용량 차감 방식
     *
     * @param clubId
     * @param requestSize
     * @return
     */
    @Override
    public boolean tryConsume(UUID clubId, int requestSize) {
        String totalKey = limitInfo.getTotalKey();
        String clubKey = limitInfo.getClubKey(clubId);

        Boolean result = rateLimitRedisTemplate.execute(
                mailLimitScript,
                List.of(totalKey, clubKey),
                limitInfo.getMaxByKey(totalKey), limitInfo.getMaxByKey(clubKey), requestSize, limitInfo.getExpireAt()
        );

        if (result == null) {
            log.error("redis 메일 발신 한도 조회 실패");
            return false;
        }

        return result;
    }
}
