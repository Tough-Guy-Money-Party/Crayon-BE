package com.yoyomo.infra.redis;

import com.yoyomo.domain.club.exception.UnavailableSubdomainException;
import com.yoyomo.infra.aws.s3.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RetryQueueProcessor {
    private static final String QUEUE_KEY = "upload:queue";
    private static final String DOMAIN_FORMAT = "%s.crayon.land";

    private final RedisTemplate<String, String> redisTemplate;
    private final S3Service s3Service;
    private final RedisQueueService redisQueueService;

    @Scheduled(fixedDelay = 7200000)
    public void processRetryQueue() {
        while (Boolean.TRUE.equals(redisTemplate.hasKey(QUEUE_KEY))) {
            String subDomain = redisQueueService.dequeueToRetryQueue();
            String fullSubDomain = String.format(DOMAIN_FORMAT, subDomain);
            try {
                s3Service.upload(fullSubDomain);
            } catch (Exception e) {
                redisQueueService.enqueueToFailedQueue(subDomain);
                throw new UnavailableSubdomainException(e.getMessage());
            }
        }
    }
}