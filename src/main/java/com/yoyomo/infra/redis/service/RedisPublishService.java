package com.yoyomo.infra.redis.service;

import com.yoyomo.infra.redis.RedisPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisPublishService {
    private final RedisPublisher redisPublisher;

    public void publishSubDomain(String subDomain) {
        redisPublisher.publishCreation(subDomain);
    }
}
