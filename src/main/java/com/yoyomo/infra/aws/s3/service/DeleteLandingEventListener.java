package com.yoyomo.infra.aws.s3.service;

import com.yoyomo.infra.aws.cloudfront.Service.CloudfrontService;
import com.yoyomo.infra.aws.dto.LandingDeleteEvent;
import com.yoyomo.infra.aws.exception.DeletionFailedException;
import com.yoyomo.infra.aws.route53.service.Route53Service;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@EnableAsync
public class DeleteLandingEventListener {
    private final S3Service s3Service;
    private final CloudfrontService cloudfrontService;
    private final Route53Service route53Service;

    @Async
    @EventListener
    public void processUpload(LandingDeleteEvent uploadEvent) {
        String subdomain = uploadEvent.subdomain();
        try {
            cloudfrontService.disableDitribute(subdomain);
            s3Service.delete(subdomain);
            route53Service.delete(subdomain);
        } catch (Exception e) {
            throw new DeletionFailedException(e);
        }
    }
}
