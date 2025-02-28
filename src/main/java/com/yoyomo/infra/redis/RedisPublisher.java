package com.yoyomo.infra.redis;

import com.yoyomo.infra.redis.service.RedisQueueService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisPublisher {

    private final RedisQueueService redisQueueService;
    private final ApplicationEventPublisher applicationEventPublisher;

    public void publishCreation(String subDomain) {
        redisQueueService.enqueue(subDomain);
        applicationEventPublisher.publishEvent(subDomain);
    }
}
