package com.yoyomo.infra.redis;

import com.mongodb.lang.Nullable;
import com.yoyomo.domain.club.exception.UnavailableSubdomainException;
import com.yoyomo.infra.aws.s3.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@EnableAsync
public class RedisQueueWorker implements MessageListener {
    private static final String DOMAIN_FORMAT = "%s.crayon.land";

    private final S3Service s3Service;
    private final RedisQueueService redisQueueService;

    @Override
    public void onMessage(Message message, @Nullable byte[] pattern) {
        processUpload();
    }

    @Async
    public void processUpload() {
        String subDomain = redisQueueService.dequeue();
        try {
            String fullSubDomain = String.format(DOMAIN_FORMAT, subDomain);
            s3Service.upload(fullSubDomain);
        } catch (Exception e) {
            redisQueueService.enqueueToRetryQueue(subDomain);
            throw new UnavailableSubdomainException(e.getMessage());
        }
    }
}
