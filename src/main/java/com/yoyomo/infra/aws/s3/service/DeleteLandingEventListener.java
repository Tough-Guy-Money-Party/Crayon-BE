package com.yoyomo.infra.aws.s3.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.yoyomo.infra.aws.cloudfront.service.CloudfrontService;
import com.yoyomo.infra.aws.dto.LandingDeleteEvent;
import com.yoyomo.infra.aws.exception.DeletionFailedException;
import com.yoyomo.infra.aws.route53.service.Route53Service;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DeleteLandingEventListener {
	private final S3Service s3Service;
	private final CloudfrontService cloudfrontService;
	private final Route53Service route53Service;

	@Async
	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
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
