package com.yoyomo.infra.aws.route53.service;


import com.yoyomo.domain.club.exception.DuplicatedSubDomainException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.route53.Route53Client;
import software.amazon.awssdk.services.route53.model.AliasTarget;
import software.amazon.awssdk.services.route53.model.Change;
import software.amazon.awssdk.services.route53.model.ChangeAction;
import software.amazon.awssdk.services.route53.model.ChangeBatch;
import software.amazon.awssdk.services.route53.model.ChangeResourceRecordSetsRequest;
import software.amazon.awssdk.services.route53.model.ListResourceRecordSetsRequest;
import software.amazon.awssdk.services.route53.model.ListResourceRecordSetsResponse;
import software.amazon.awssdk.services.route53.model.RRType;
import software.amazon.awssdk.services.route53.model.ResourceRecordSet;

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

        route53Client.changeResourceRecordSets(request);
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

        route53Client.changeResourceRecordSets(deleteRequest);
    }

    public void checkDuplication(String subdomain) {
        ListResourceRecordSetsRequest listRequest = ListResourceRecordSetsRequest.builder()
                .hostedZoneId(hostedId)
                .startRecordName(subdomain + ".crayon.land.")
                .startRecordType(RRType.A)
                .build();

        ListResourceRecordSetsResponse listResponse = route53Client.listResourceRecordSets(listRequest);

        boolean duplicated = listResponse.resourceRecordSets().stream()
                .anyMatch(rrs ->
                        rrs.name().equals(subdomain + ".crayon.land.")
                                && rrs.type() == RRType.A
                );

        if (duplicated) {
            throw new DuplicatedSubDomainException();
        }


    }


}
