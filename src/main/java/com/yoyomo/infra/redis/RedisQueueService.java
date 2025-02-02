package com.yoyomo.infra.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisQueueService {
    private static final String QUEUE_NAME = "uploadQueue";

    private final RedisTemplate<String, String> queueRedisTemplate;

    public RedisQueueService(RedisTemplate<String, String> queueRedisTemplate) {
        this.queueRedisTemplate = queueRedisTemplate;
    }

    public void enqueueUpload(String subDomain) {
        queueRedisTemplate.opsForList().leftPush(QUEUE_NAME, subDomain);
    }

    public String dequeueUpload() {
        return queueRedisTemplate.opsForList().rightPop(QUEUE_NAME);
    }
}


