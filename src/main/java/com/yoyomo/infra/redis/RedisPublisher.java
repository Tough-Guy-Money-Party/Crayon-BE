package com.yoyomo.infra.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisPublisher {
    private static final String QUEUE_KEY = "upload:queue";
    private static final String QUEUE_TOPIC = "upload:topic";
    private final RedisTemplate<String, String> queueRedisTemplate;
    private final RedisTemplate<String, String> redisTemplate;

    public void publishCreation(String subDomain) {
        queueRedisTemplate.opsForList().rightPush(QUEUE_KEY, subDomain);
        redisTemplate.convertAndSend(QUEUE_TOPIC, subDomain);
    }

    public String dequeueUpload() {
        return queueRedisTemplate.opsForList().leftPop(QUEUE_KEY);
    }
}
