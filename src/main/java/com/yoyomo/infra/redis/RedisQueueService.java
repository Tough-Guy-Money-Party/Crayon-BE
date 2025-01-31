package com.yoyomo.infra.redis;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisQueueService {
    private static final String QUEUE_NAME = "uploadQueue";

    private final RedisTemplate<String, String> redisTemplate;

    public RedisQueueService(@Qualifier("queueRedisTemplate") RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void enqueueUpload(String subDomain) {
        redisTemplate.opsForList().leftPush(QUEUE_NAME, subDomain);
    }

    public String dequeueUpload() {
        return redisTemplate.opsForList().rightPop(QUEUE_NAME);
    }
}


