package com.yoyomo.infra.aws.lambda.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.lambda.LambdaAsyncClient;

@Configuration
public class LambdaConfig {

	@Value("${cloud.aws.credentials.mailAccessKey}")
	private String accessKey;

	@Value("${cloud.aws.credentials.mailSecretKey}")
	private String secretKey;

	@Value("${cloud.aws.region.static}")
	private String region;

	@Bean
	public LambdaAsyncClient lambdaAsyncClient() {
		return LambdaAsyncClient.builder()
			.region(Region.of(region))
			.credentialsProvider(StaticCredentialsProvider.create(
				AwsBasicCredentials.create(accessKey, secretKey)))
			.build();
	}
}
