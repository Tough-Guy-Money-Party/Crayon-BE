package com.yoyomo.infra.aws.dynamodb.config;

import com.yoyomo.domain.mail.domain.entity.Mail;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;

@Configuration
public class AwsDynamodbConfig {
    private static final String TABLE_NAME = "MailTable";
    @Value("${cloud.aws.credentials.mailAccessKey}")
    private String accessKey;
    @Value("${cloud.aws.credentials.mailSecretKey}")
    private String secretKey;
    @Value("${cloud.aws.region.static}")
    private String region;

    @Bean //재시도 횟수: 기본적으로 최대 3회 재시도
    public DynamoDbAsyncClient dynamoDbAsyncClient() {
        return DynamoDbAsyncClient.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(accessKey, secretKey)))
                .build();
    }

    @Bean
    public DynamoDbEnhancedAsyncClient dynamoDbEnhancedAsyncClient(DynamoDbAsyncClient dynamoDbAsyncClient) {
        return DynamoDbEnhancedAsyncClient.builder()
                .dynamoDbClient(dynamoDbAsyncClient)
                .build();
    }

    @Bean
    public DynamoDbAsyncTable<Mail> mailTable(DynamoDbEnhancedAsyncClient dynamoDbEnhancedAsyncClient) {
        return dynamoDbEnhancedAsyncClient.table(TABLE_NAME, TableSchema.fromBean(Mail.class));
    }
}


