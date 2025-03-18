package com.yoyomo.infra.aws.s3.service;

import com.yoyomo.domain.club.exception.UnavailableSubdomainException;
import com.yoyomo.infra.aws.dto.LandingClientUploadEvent;
import com.yoyomo.infra.redis.service.RedisQueueService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@EnableAsync
public class UploadEventListener {
    private final S3Service s3Service;
    private final RedisQueueService redisQueueService;

    @Async
    @EventListener
    public void processUpload(LandingClientUploadEvent uploadEvent) {
        String subdomain = uploadEvent.subdomain();
        try {
            s3Service.upload(subdomain);
        } catch (Exception e) {
            retryUpload(subdomain);
        }
    }

    private void retryUpload(String subdomain) {
        try {
            s3Service.upload(subdomain);
        } catch (Exception e) {
            redisQueueService.enqueueToFailedQueue(subdomain);
            throw new UnavailableSubdomainException(e.getMessage());
        }
    }
}
