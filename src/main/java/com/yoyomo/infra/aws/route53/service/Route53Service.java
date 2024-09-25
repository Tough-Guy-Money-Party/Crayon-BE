package com.yoyomo.infra.aws.route53.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.route53.Route53Client;
import software.amazon.awssdk.services.route53.model.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class Route53Service {
    private final Route53Client route53Client;

    @Value("${cloud.aws.route53.hostedZoneId}")
    private String hostedId;

    public void create(String subdomain, String cloudFrontDomainName) {
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
                .hostedZoneId(hostedId)
                .changeBatch(changeBatch)
                .build();

        ChangeResourceRecordSetsResponse response = route53Client.changeResourceRecordSets(request);
        Route53Service.log.info("Route 53 record created: " + response.changeInfo().statusAsString());
    }


}
