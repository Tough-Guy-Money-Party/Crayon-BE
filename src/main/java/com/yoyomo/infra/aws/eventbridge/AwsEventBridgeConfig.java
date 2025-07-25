package com.yoyomo.infra.aws.eventbridge;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.scheduler.SchedulerAsyncClient;
import software.amazon.awssdk.services.scheduler.SchedulerClient;

@Configuration
public class AwsEventBridgeConfig {

	@Value("${cloud.aws.credentials.mailAccessKey}")
	private String accessKey;

	@Value("${cloud.aws.credentials.mailSecretKey}")
	private String secretKey;

	@Value("${cloud.aws.region.static}")
	private String region;

	@Bean
	public SchedulerClient schedulerClient() {
		return SchedulerClient.builder()
			.credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey)))
			.region(Region.of(region))
			.build();
	}

	@Bean
	public SchedulerAsyncClient schedulerAsyncClient() {
		return SchedulerAsyncClient.builder()
			.credentialsProvider(StaticCredentialsProvider.create((AwsBasicCredentials.create(accessKey, secretKey))))
			.region(Region.of(region))
			.build();
	}
}
