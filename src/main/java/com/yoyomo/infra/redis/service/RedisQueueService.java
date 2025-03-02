package com.yoyomo.infra.redis.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisQueueService {
    private static final String QUEUE_KEY = "upload:queue";
    private static final String RETRY_QUEUE_KEY = "retry:queue";
    private static final String FAILED_QUEUE_KEY = "failed:queue";

    private final RedisTemplate<String, String> queueRedisTemplate;

    public void enqueue(String subDomain) {
        queueRedisTemplate.opsForList().rightPush(QUEUE_KEY, subDomain);
    }

    public String dequeue() {
        return queueRedisTemplate.opsForList().leftPop(QUEUE_KEY);
    }

    public void enqueueToRetryQueue(String subDomain) {
        queueRedisTemplate.opsForList().rightPush(RETRY_QUEUE_KEY, subDomain);
    }

    public String dequeueToRetryQueue() {
        return queueRedisTemplate.opsForList().leftPop(RETRY_QUEUE_KEY);
    }

    public void enqueueToFailedQueue(String subDomain) {
        queueRedisTemplate.opsForList().rightPush(FAILED_QUEUE_KEY, subDomain);
    }
}


