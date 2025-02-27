package com.yoyomo.infra.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisService {
    private final RedisPublisher redisPublisher;

    public void publishSubDomain(String subDomain) {
        redisPublisher.publishCreation(subDomain);
    }
}
