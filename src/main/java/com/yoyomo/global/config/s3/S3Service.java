package com.yoyomo.global.config.s3;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.cloudfront.CloudFrontClient;
import software.amazon.awssdk.services.lambda.LambdaClient;
import software.amazon.awssdk.services.route53.Route53Client;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.PublicAccessBlockConfiguration;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final S3Client s3Client;

    public void createBucket(String bucketName) {
        CreateBucketRequest createBucketRequest = CreateBucketRequest.builder()
                .bucket(bucketName)
                .build();

        s3Client.createBucket(createBucketRequest);

        s3Client.putPublicAccessBlock(b -> b.bucket(bucketName)
                .publicAccessBlockConfiguration(PublicAccessBlockConfiguration.builder()
                        .blockPublicAcls(false)
                        .ignorePublicAcls(false)
                        .blockPublicPolicy(false)
                        .restrictPublicBuckets(false)
                        .build()));

        System.out.println("Bucket created: " + bucketName);
    }

}
