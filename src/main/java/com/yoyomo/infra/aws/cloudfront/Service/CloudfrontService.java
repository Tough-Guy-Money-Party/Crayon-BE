package com.yoyomo.infra.aws.cloudfront.Service;

import com.yoyomo.infra.aws.exception.DistributeNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.cloudfront.CloudFrontClient;
import software.amazon.awssdk.services.cloudfront.model.*;

@Service
@RequiredArgsConstructor
public class CloudfrontService {

    private static final Logger logger = LoggerFactory.getLogger(CloudfrontService.class);
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
                .originProtocolPolicy(OriginProtocolPolicy.HTTP_ONLY)
                .httpPort(80)
                .httpsPort(443)
                .build();

        Origin origin = Origin.builder()
                .domainName(domainName)
                .id(subDomain)
                .customOriginConfig(customOriginConfig)
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
        String nextMarker = null;
        List<DistributionSummary> allDistributions = new ArrayList<>();

        do {
            ListDistributionsResponse listDistributionsResponse = cloudFrontClient.listDistributions(
                    ListDistributionsRequest.builder()
                            .marker(nextMarker)
                            .build()
            );

            allDistributions.addAll(listDistributionsResponse.distributionList().items());
            nextMarker = listDistributionsResponse.distributionList().nextMarker();
        } while (nextMarker != null);

        return allDistributions.stream()
                .filter(distribution -> distribution.aliases().items().contains(subDomain))
                .findFirst()
                .map(DistributionSummary::id)
                .orElseThrow(DistributeNotFoundException::new);
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
        logger.info("CloudFront distribution disabled: {}", distributionId);
    }

    @Scheduled(cron = "0 0 3 * * ?")
    public void deleteInactiveDistributions() {
        logger.info("배포 삭제 시작");
        List<DistributionSummary> inactiveDistributions = findInactiveDistributions();
        inactiveDistributions.forEach(this::deleteDistribution);
    }

    private List<DistributionSummary> findInactiveDistributions() {
        List<DistributionSummary> allDistributions = new ArrayList<>();
        String nextMarker = null;

        do {
            ListDistributionsResponse response = cloudFrontClient.listDistributions(ListDistributionsRequest.builder().marker(nextMarker).build());
            allDistributions.addAll(response.distributionList().items());
            nextMarker = response.distributionList().nextMarker();
        } while (nextMarker != null);

        return allDistributions.stream()
                .filter(distribution -> distribution.enabled().equals(false))
                .toList();
    }

    public void deleteDistribution(DistributionSummary distribution) {
        String distributionId = distribution.id();
        try {
            GetDistributionConfigRequest getDistributionConfigRequest = GetDistributionConfigRequest.builder()
                    .id(distributionId)
                    .build();

            GetDistributionConfigResponse getDistributionConfigResponse = cloudFrontClient.getDistributionConfig(getDistributionConfigRequest);

            DeleteDistributionRequest deleteRequest = DeleteDistributionRequest.builder()
                    .id(distributionId)
                    .ifMatch(getDistributionConfigResponse.eTag())
                    .build();

            cloudFrontClient.deleteDistribution(deleteRequest);
            logger.info("배포 삭제 완료: {}", distributionId);
        } catch (CloudFrontException e) {
            logger.error("배포 삭제 실패: {} - {}", distributionId, e.awsErrorDetails().errorMessage());
        }
    }
}
