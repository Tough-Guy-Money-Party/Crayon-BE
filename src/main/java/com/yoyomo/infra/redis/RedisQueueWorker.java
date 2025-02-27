package com.yoyomo.infra.redis;

import com.yoyomo.domain.club.exception.UnavailableSubdomainException;
import com.yoyomo.infra.aws.s3.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisQueueWorker implements MessageListener {
    private static final String DOMAIN_FORMAT = "%s.crayon.land";

    private final RedisPublisher redisPublisher;
    private final S3Service s3Service;

    @Override
    public void onMessage(Message message, @Nullable byte[] pattern) {
        processUploadQueue();
    }

    public void processUploadQueue() {
        String subDomain = redisPublisher.dequeueUpload();
        while (subDomain != null) {
            try {
                String fullSubDomain = String.format(DOMAIN_FORMAT, subDomain);
                s3Service.upload(fullSubDomain);
            } catch (Exception e) {
                throw new UnavailableSubdomainException(e.getMessage());
            }
            subDomain = redisPublisher.dequeueUpload();
        }
    }
}
