package com.yoyomo.infra.redis;

import com.yoyomo.infra.redis.service.RedisQueueService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisPublisher {
    private static final String QUEUE_TOPIC = "upload:topic";

    private final RedisTemplate<String, String> redisTemplate;
    private final RedisQueueService redisQueueService;

    public void publishCreation(String subDomain) {
        redisQueueService.enqueue(subDomain);
        redisTemplate.convertAndSend(QUEUE_TOPIC, subDomain);
    }
}
