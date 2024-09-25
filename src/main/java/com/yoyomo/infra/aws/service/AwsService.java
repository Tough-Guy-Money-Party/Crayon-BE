package com.yoyomo.infra.aws.service;


import com.yoyomo.infra.aws.cloudfront.Service.CloudfrontService;
import com.yoyomo.infra.aws.route53.service.Route53Service;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AwsService {
    private final CloudfrontService cloudfrontService;
    private final Route53Service route53Service;

    public void distribute(String subDomain) {

        String distributeId = cloudfrontService.create(subDomain);
        route53Service.create(subDomain, cloudfrontService.getCloudfrontDomainName(distributeId));
    }
}
