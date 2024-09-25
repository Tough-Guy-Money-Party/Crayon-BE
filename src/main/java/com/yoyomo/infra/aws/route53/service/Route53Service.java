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

    public void delete(String subdomain) {

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

        ChangeBatch changeBatch = ChangeBatch.builder()
                .changes(Change.builder()
                        .action(ChangeAction.DELETE)
                        .resourceRecordSet(recordSet)
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
