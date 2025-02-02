package com.yoyomo.infra.redis;

import com.yoyomo.domain.club.exception.UnavailableSubdomainException;
import com.yoyomo.infra.aws.s3.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisQueueWorker {
    private static final String DOMAIN_FORMAT = "%s.crayon.land";
    
    private final RedisQueueService redisQueueService;
    private final S3Service s3Service;

    @Scheduled(fixedDelay = 1000)
    public void processUploadQueue() {
        while (true) {
            String subDomain = redisQueueService.dequeueUpload();
            if (subDomain == null) {
                break;
            }

            try {
                String fullSubDomain = String.format(DOMAIN_FORMAT, subDomain);
                s3Service.upload(fullSubDomain);
            } catch (Exception e) {
                throw new UnavailableSubdomainException(e.getMessage());
            }
        }
    }
}

