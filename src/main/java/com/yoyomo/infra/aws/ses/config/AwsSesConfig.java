package com.yoyomo.infra.aws.ses.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ses.SesClient;

@Configuration
public class AwsSesConfig {
    @Value("${cloud.aws.credentials.mailAccessKey}")
    private String accessKey;

    @Value("${cloud.aws.credentials.mailSecretKey}")
    private String secretKey;

    @Value("${cloud.aws.region.static}")
    private String region;

    @Bean
    public SesClient sesClient() {	// SES V2 사용 시 SesV2Client
        AwsBasicCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);
        return SesClient.builder()
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .region(Region.of(region))
                .build();
    }
}
