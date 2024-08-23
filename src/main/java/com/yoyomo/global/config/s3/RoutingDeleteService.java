package com.yoyomo.global.config.s3;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.cloudfront.CloudFrontClient;
import software.amazon.awssdk.services.cloudfront.model.*;
import software.amazon.awssdk.services.route53.Route53Client;
import software.amazon.awssdk.services.route53.model.*;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteBucketRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;

@Service
@RequiredArgsConstructor
public class RoutingDeleteService {
    private final CloudFrontClient cloudFrontClient;
    private final Route53Client route53Client;
    private final S3Client s3Client;

    @Value("${cloud.aws.route53.hostedZoneId}")
    private String hostedId;

    public void deleteResources(String subdomain) {
        // 1. CloudFront 배포 ID 찾기
        String distributionId = findCloudFrontDistributionId(subdomain);

        // 2. CloudFront 배포 비활성화
        disableCloudFrontDistribution(distributionId);

        // 3. S3 버킷 삭제
        deleteS3Bucket(subdomain);

        // 4. Route 53 레코드 삭제
        deleteRoute53Record(subdomain);
    }

    private String findCloudFrontDistributionId(String subdomain) {
        ListDistributionsResponse listDistributionsResponse = cloudFrontClient.listDistributions();

        return listDistributionsResponse.distributionList().items().stream()
                .filter(distribution -> distribution.aliases().items().contains(subdomain))
                .findFirst()
                .map(DistributionSummary::id)
                .orElseThrow(() -> new RuntimeException("CloudFront distribution not found for subdomain: " + subdomain));
    }

    private void disableCloudFrontDistribution(String distributionId) {
        // 기존 배포 구성을 가져옵니다.
        GetDistributionConfigRequest getDistributionConfigRequest = GetDistributionConfigRequest.builder()
                .id(distributionId)
                .build();
        GetDistributionConfigResponse getDistributionConfigResponse = cloudFrontClient.getDistributionConfig(getDistributionConfigRequest);

        // 배포를 비활성화합니다.
        DistributionConfig distributionConfig = getDistributionConfigResponse.distributionConfig().toBuilder()
                .enabled(false)  // 배포 비활성화
                .build();

        UpdateDistributionRequest updateDistributionRequest = UpdateDistributionRequest.builder()
                .id(distributionId)
                .distributionConfig(distributionConfig)
                .ifMatch(getDistributionConfigResponse.eTag())  // ETag 사용
                .build();

        cloudFrontClient.updateDistribution(updateDistributionRequest);
        System.out.println("CloudFront distribution disabled: " + distributionId);
    }

    private void deleteS3Bucket(String bucketName) {
        // 버킷 내 모든 객체 삭제
        ListObjectsV2Request listObjectsRequest = ListObjectsV2Request.builder()
                .bucket(bucketName)
                .build();

        ListObjectsV2Response listObjectsResponse;
        do {
            listObjectsResponse = s3Client.listObjectsV2(listObjectsRequest);
            listObjectsResponse.contents().forEach(s3Object -> {
                s3Client.deleteObject(builder -> builder.bucket(bucketName).key(s3Object.key()).build());
            });

            listObjectsRequest = listObjectsRequest.toBuilder()
                    .continuationToken(listObjectsResponse.nextContinuationToken())
                    .build();

        } while (listObjectsResponse.isTruncated());

        // 버킷 삭제
        s3Client.deleteBucket(DeleteBucketRequest.builder()
                .bucket(bucketName)
                .build());
        System.out.println("S3 bucket deleted: " + bucketName);
    }

    public void deleteRoute53Record(String subdomain) {
        // 1. 현재 레코드 조회
        ListResourceRecordSetsRequest listRequest = ListResourceRecordSetsRequest.builder()
                .hostedZoneId(hostedId)
                .startRecordName(subdomain)
                .startRecordType(RRType.A)
                .build();

        ListResourceRecordSetsResponse listResponse = route53Client.listResourceRecordSets(listRequest);

        ResourceRecordSet recordSet = listResponse.resourceRecordSets().stream()
                .filter(rrs -> rrs.name().equals(subdomain + ".") && rrs.type() == RRType.A)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Record set not found for subdomain: " + subdomain));

        // 2. 조회된 레코드 값으로 삭제 요청
        ChangeBatch changeBatch = ChangeBatch.builder()
                .changes(Change.builder()
                        .action(ChangeAction.DELETE)
                        .resourceRecordSet(recordSet) // 조회된 레코드를 그대로 사용
                        .build())
                .build();

        ChangeResourceRecordSetsRequest deleteRequest = ChangeResourceRecordSetsRequest.builder()
                .hostedZoneId(hostedId)
                .changeBatch(changeBatch)
                .build();

        ChangeResourceRecordSetsResponse response = route53Client.changeResourceRecordSets(deleteRequest);
        System.out.println("Route 53 record deleted for subdomain: " + subdomain);
    }

}
