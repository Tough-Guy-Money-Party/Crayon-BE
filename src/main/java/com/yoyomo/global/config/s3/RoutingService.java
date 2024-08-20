package com.yoyomo.global.config.s3;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.cloudfront.CloudFrontClient;
import software.amazon.awssdk.services.cloudfront.model.*;
import software.amazon.awssdk.services.lambda.LambdaClient;
import software.amazon.awssdk.services.lambda.model.InvokeRequest;
import software.amazon.awssdk.services.lambda.model.InvokeResponse;
import software.amazon.awssdk.services.route53.Route53Client;
import software.amazon.awssdk.services.route53.model.*;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.PutBucketPolicyRequest;
import software.amazon.awssdk.services.s3.model.PublicAccessBlockConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoutingService {
    private final S3Client s3Client;
    private final CloudFrontClient cloudFrontClient;
    private final LambdaClient lambdaClient;
    private final Route53Client route53Client;

    @Value("${cloud.aws.accountId}")
    private String accountId;

    @Value("${cloud.aws.acm}")
    private String acm;

    @Value("${cloud.aws.route53.hostedZoneId}")
    private String hostedId;

    private String createCloudFrontDistribution(String bucketName, String region, String oaiId, String acmCertificateArn, String originShieldRegion, String alternateDomainName) {
        String callerReference = Long.toString(System.currentTimeMillis());

        String domainName = bucketName + ".s3." + region + ".amazonaws.com";

        OriginShield originShield = OriginShield.builder()
                .enabled(true)
                .originShieldRegion(originShieldRegion)
                .build();

        Origin origin = Origin.builder()
                .domainName(domainName)
                .id(bucketName)
                .s3OriginConfig(S3OriginConfig.builder()
                        .originAccessIdentity("origin-access-identity/cloudfront/" + oaiId)
                        .build())
                .originShield(originShield)
                .build();

        Origins origins = Origins.builder()
                .items(origin)
                .quantity(1)
                .build();

        ForwardedValues forwardedValues = ForwardedValues.builder()
                .queryString(false)
                .cookies(CookiePreference.builder().forward("none").build())
                .build();

        DefaultCacheBehavior cacheBehavior = DefaultCacheBehavior.builder()
                .targetOriginId(bucketName)
                .viewerProtocolPolicy(ViewerProtocolPolicy.REDIRECT_TO_HTTPS)
                .minTTL(0L)
                .forwardedValues(forwardedValues)
                .build();

        ViewerCertificate viewerCertificate = ViewerCertificate.builder()
                .acmCertificateArn(acmCertificateArn)
                .sslSupportMethod(SSLSupportMethod.SNI_ONLY)
                .minimumProtocolVersion(MinimumProtocolVersion.TLS_V1_2_2019)
                .build();

        DistributionConfig distributionConfig = DistributionConfig.builder()
                .callerReference(callerReference)
                .origins(origins)
                .defaultCacheBehavior(cacheBehavior)
                .comment("S3 bucket distribution for " + bucketName)
                .enabled(true)
                .defaultRootObject("/index.html")
                .viewerCertificate(viewerCertificate)
                .aliases(Aliases.builder()
                        .items(alternateDomainName)
                        .quantity(1)
                        .build())
                .build();

        CreateDistributionRequest distributionRequest = CreateDistributionRequest.builder()
                .distributionConfig(distributionConfig)
                .build();

        CreateDistributionResponse distributionResponse = cloudFrontClient.createDistribution(distributionRequest);
        return distributionResponse.distribution().id();
    }

    private void updateBucketPolicyWithOAI(String bucketName, String oaiId, String distributionId) {
        String policyJson = String.format(
                "{\n" +
                        "  \"Version\": \"2008-10-17\",\n" +
                        "  \"Id\": \"PolicyForCloudFrontPrivateContent\",\n" +
                        "  \"Statement\": [\n" +
                        "    {\n" +
                        "      \"Sid\": \"AllowCloudFrontServicePrincipal\",\n" +
                        "      \"Effect\": \"Allow\",\n" +
                        "      \"Principal\": {\n" +
                        "        \"Service\": \"cloudfront.amazonaws.com\"\n" +
                        "      },\n" +
                        "      \"Action\": \"s3:GetObject\",\n" +
                        "      \"Resource\": \"arn:aws:s3:::%s/*\",\n" +
                        "      \"Condition\": {\n" +
                        "        \"StringEquals\": {\n" +
                        "          \"AWS:SourceArn\": \"arn:aws:cloudfront::%s:distribution/%s\"\n" +
                        "        }\n" +
                        "      }\n" +
                        "    }\n" +
                        "  ]\n" +
                        "}", bucketName, accountId, distributionId);

        s3Client.putBucketPolicy(PutBucketPolicyRequest.builder()
                .bucket(bucketName)
                .policy(policyJson)
                .build());

        System.out.println("Bucket policy updated for OAI: " + oaiId);
    }

    public void handleS3Upload(String bucketName, String region, String subdomain) {
        String oaiId = createOriginAccessIdentity();

        String distributionId = createCloudFrontDistribution(bucketName, region, oaiId, acm, region, subdomain);

        updateBucketPolicyWithOAI(bucketName, oaiId, distributionId);

        updateCloudFrontDistributionWithOAIAndCustomErrorPage(distributionId, oaiId);

        invalidateCache(distributionId, "/*");

        String cloudFrontDomainName = getCloudFrontDomainName(distributionId);

        createRoute53Record(hostedId, subdomain, cloudFrontDomainName);

        invokeLambdaOnS3Upload(distributionId, bucketName, oaiId, "updateOAI");
    }

    private String getCloudFrontDomainName(String distributionId) {
        GetDistributionRequest getDistributionRequest = GetDistributionRequest.builder()
                .id(distributionId)
                .build();

        GetDistributionResponse getDistributionResponse = cloudFrontClient.getDistribution(getDistributionRequest);
        return getDistributionResponse.distribution().domainName();
    }

    public void invokeLambdaOnS3Upload(String distributionId, String bucketName, String oaiId, String functionName) {
        String payload = String.format("{ \"distributionId\": \"%s\", \"bucketName\": \"%s\", \"oaiId\": \"%s\" }", distributionId, bucketName, oaiId);

        InvokeRequest invokeRequest = InvokeRequest.builder()
                .functionName(functionName)
                .payload(SdkBytes.fromUtf8String(payload))
                .build();

        InvokeResponse invokeResponse = lambdaClient.invoke(invokeRequest);
        String responsePayload = invokeResponse.payload().asUtf8String();

        System.out.println("Lambda function invoked with response: " + responsePayload);
    }

    private void updateCloudFrontDistributionWithOAIAndCustomErrorPage(String distributionId, String oaiId) {
        // 기존 배포 구성 요청
        GetDistributionConfigRequest getDistributionConfigRequest = GetDistributionConfigRequest.builder()
                .id(distributionId)
                .build();
        GetDistributionConfigResponse getDistributionConfigResponse = cloudFrontClient.getDistributionConfig(getDistributionConfigRequest);

        // 기존 Origin 설정 업데이트
        DistributionConfig distributionConfig = getDistributionConfigResponse.distributionConfig().toBuilder()
                .origins(origins -> origins.items(
                        getDistributionConfigResponse.distributionConfig().origins().items().stream()
                                .map(origin -> {
                                    if (origin.s3OriginConfig() != null) {
                                        return origin.toBuilder()
                                                .s3OriginConfig(origin.s3OriginConfig().toBuilder()
                                                        .originAccessIdentity("origin-access-identity/cloudfront/" + oaiId)
                                                        .build())
                                                .build();
                                    }
                                    return origin;
                                })
                                .collect(Collectors.toList())
                ).quantity(getDistributionConfigResponse.distributionConfig().origins().items().size()))
                .customErrorResponses(customErrorResponses -> customErrorResponses.items(
                        Arrays.asList(
                                CustomErrorResponse.builder()
                                        .errorCode(403)
                                        .responsePagePath("/index.html")
                                        .responseCode("200")
                                        .errorCachingMinTTL(300L) // 캐싱 시간, 필요에 따라 조정
                                        .build()
                        )
                ).quantity(1)) // 커스텀 오류 페이지 수량
                .build();

        // 배포 업데이트 요청
        UpdateDistributionRequest updateDistributionRequest = UpdateDistributionRequest.builder()
                .id(distributionId)
                .distributionConfig(distributionConfig)
                .ifMatch(getDistributionConfigResponse.eTag())
                .build();

        // CloudFront 배포 업데이트
        cloudFrontClient.updateDistribution(updateDistributionRequest);
        System.out.println("CloudFront 배포의 OAI 및 커스텀 오류 페이지가 업데이트되었습니다.");
    }


    private String createOriginAccessIdentity() {
        CreateCloudFrontOriginAccessIdentityRequest request = CreateCloudFrontOriginAccessIdentityRequest.builder()
                .cloudFrontOriginAccessIdentityConfig(
                        CloudFrontOriginAccessIdentityConfig.builder()
                                .callerReference(Long.toString(System.currentTimeMillis()))
                                .comment("OAI for accessing S3 bucket from CloudFront")
                                .build()
                ).build();

        CreateCloudFrontOriginAccessIdentityResponse response = cloudFrontClient.createCloudFrontOriginAccessIdentity(request);
        return response.cloudFrontOriginAccessIdentity().id();
    }

    public void invalidateCache(String distributionId, String path) {
        String callerReference = Long.toString(System.currentTimeMillis());

        InvalidationBatch invalidationBatch = InvalidationBatch.builder()
                .paths(builder -> builder.items(Collections.singletonList(path)).quantity(1))
                .callerReference(callerReference)
                .build();

        CreateInvalidationRequest invalidationRequest = CreateInvalidationRequest.builder()
                .distributionId(distributionId)
                .invalidationBatch(invalidationBatch)
                .build();

        CreateInvalidationResponse response = cloudFrontClient.createInvalidation(invalidationRequest);
        System.out.println("Invalidation created with ID: " + response.invalidation().id());
    }

    public void createRoute53Record(String hostedZoneId, String subdomain, String cloudFrontDomainName) {
        ChangeBatch changeBatch = ChangeBatch.builder()
                .changes(Change.builder()
                        .action(ChangeAction.UPSERT)
                        .resourceRecordSet(ResourceRecordSet.builder()
                                .name(subdomain)
                                .type(RRType.A)
                                .aliasTarget(AliasTarget.builder()
                                        .dnsName(cloudFrontDomainName)
                                        .hostedZoneId("Z2FDTNDATAQYW2")
                                        .evaluateTargetHealth(false)
                                        .build())
                                .build())
                        .build())
                .build();

        ChangeResourceRecordSetsRequest request = ChangeResourceRecordSetsRequest.builder()
                .hostedZoneId(hostedZoneId)
                .changeBatch(changeBatch)
                .build();

        ChangeResourceRecordSetsResponse response = route53Client.changeResourceRecordSets(request);
        System.out.println("Route 53 record created: " + response.changeInfo().statusAsString());
    }
}
