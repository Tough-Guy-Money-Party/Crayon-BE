package com.yoyomo.infra.aws.s3.service;

import com.yoyomo.domain.club.exception.UnavailableSubdomainException;
import com.yoyomo.infra.aws.cloudfront.Service.CloudfrontService;
import com.yoyomo.infra.aws.dto.LandingCreateEvent;
import com.yoyomo.infra.aws.route53.service.Route53Service;
import com.yoyomo.infra.redis.service.RedisQueueService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class LandingCreateEventListener {
    private final S3Service s3Service;
    private final CloudfrontService cloudfrontService;
    private final Route53Service route53Service;

    private final RedisQueueService redisQueueService;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void processUpload(LandingCreateEvent uploadEvent) {
        String subdomain = uploadEvent.subdomain();
        try {
            s3Service.createBucket(subdomain);
            String distributeId = cloudfrontService.create(subdomain);
            String cloudfrontDomainName = cloudfrontService.getCloudfrontDomainName(distributeId);
            route53Service.create(subdomain, cloudfrontDomainName);

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
