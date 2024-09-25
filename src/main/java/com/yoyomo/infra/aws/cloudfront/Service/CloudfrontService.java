package com.yoyomo.infra.aws.cloudfront.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.cloudfront.CloudFrontClient;
import software.amazon.awssdk.services.cloudfront.model.*;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CloudfrontService {

    private final CloudFrontClient cloudFrontClient;

    @Value("${cloud.aws.acm}")
    private String acm;
    @Value("${cloud.aws.region.static}")
    private String region;

    public String create(String subDomain) {
        String callerReference = Long.toString(System.currentTimeMillis());

        String domainName = subDomain + ".s3." + region + ".amazonaws.com";

        OriginShield originShield = OriginShield.builder()
                .enabled(true)
                .originShieldRegion(region)
                .build();

        CustomOriginConfig customOriginConfig = CustomOriginConfig.builder()
                .originProtocolPolicy(OriginProtocolPolicy.HTTP_ONLY) // 또는 HTTPS_ONLY 사용 가능
                .httpPort(80)
                .httpsPort(443)
                .build();

        Origin origin = Origin.builder()
                .domainName(domainName)
                .id(subDomain)
                .customOriginConfig(customOriginConfig) // CustomOriginConfig 사용
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
                .targetOriginId(subDomain)
                .viewerProtocolPolicy(ViewerProtocolPolicy.REDIRECT_TO_HTTPS)
                .minTTL(0L)
                .maxTTL(0L)
                .forwardedValues(forwardedValues)
                .build();

        ViewerCertificate viewerCertificate = ViewerCertificate.builder()
                .acmCertificateArn(acm)
                .sslSupportMethod(SSLSupportMethod.SNI_ONLY)
                .minimumProtocolVersion(MinimumProtocolVersion.TLS_V1_2_2019)
                .build();

        DistributionConfig distributionConfig = DistributionConfig.builder()
                .callerReference(callerReference)
                .origins(origins)
                .defaultCacheBehavior(cacheBehavior)
                .comment("S3 bucket distribution for " + subDomain)
                .enabled(true)
                .defaultRootObject("/index.html")
                .viewerCertificate(viewerCertificate)
                .aliases(Aliases.builder()
                        .items(subDomain)
                        .quantity(1)
                        .build())
                .customErrorResponses(customErrorResponses -> customErrorResponses.items(
                        Collections.singletonList(
                                CustomErrorResponse.builder()
                                        .errorCode(403)
                                        .responsePagePath("/index.html")
                                        .responseCode("200")
                                        .errorCachingMinTTL(300L)
                                        .build()
                        )
                ).quantity(1))
                .build();

        CreateDistributionRequest distributionRequest = CreateDistributionRequest.builder()
                .distributionConfig(distributionConfig)
                .build();

        CreateDistributionResponse distributionResponse = cloudFrontClient.createDistribution(distributionRequest);
        return distributionResponse.distribution().id();
    }

    public String getCloudfrontDomainName(String distributionId) {
        GetDistributionRequest getDistributionRequest = GetDistributionRequest.builder()
                .id(distributionId)
                .build();

        GetDistributionResponse getDistributionResponse = cloudFrontClient.getDistribution(getDistributionRequest);
        return getDistributionResponse.distribution().domainName();
    }

    public String findDistributionId(String subDomain) {
        ListDistributionsResponse listDistributionsResponse = cloudFrontClient.listDistributions();

        return listDistributionsResponse.distributionList().items().stream()
                .filter(distribution -> distribution.aliases().items().contains(subDomain))
                .findFirst()
                .map(DistributionSummary::id)
                .orElseThrow(() -> new RuntimeException("CloudFront distribution not found for subdomain: " + subDomain));
    }

    public void disableDitribute(String subDomain) {
        String distributionId = findDistributionId(subDomain);
        GetDistributionConfigRequest getDistributionConfigRequest = GetDistributionConfigRequest.builder()
                .id(distributionId)
                .build();

        GetDistributionConfigResponse getDistributionConfigResponse = cloudFrontClient.getDistributionConfig(getDistributionConfigRequest);

        DistributionConfig distributionConfig = getDistributionConfigResponse.distributionConfig().toBuilder()
                .enabled(false)
                .build();

        UpdateDistributionRequest updateDistributionRequest = UpdateDistributionRequest.builder()
                .id(distributionId)
                .distributionConfig(distributionConfig)
                .ifMatch(getDistributionConfigResponse.eTag())
                .build();

        cloudFrontClient.updateDistribution(updateDistributionRequest);
        System.out.println("CloudFront distribution disabled: " + distributionId);
    }

}
