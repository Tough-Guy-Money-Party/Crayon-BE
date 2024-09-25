package com.yoyomo.infra.aws.service;


import com.yoyomo.infra.aws.cloudfront.Service.CloudfrontService;
import com.yoyomo.infra.aws.route53.service.Route53Service;
import com.yoyomo.infra.aws.s3.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AwsService {
    private final CloudfrontService cloudfrontService;
    private final Route53Service route53Service;
    private final S3Service s3Service;

    public void distribute(String subDomain) {

        String distributeId = cloudfrontService.create(subDomain);
        route53Service.create(subDomain, cloudfrontService.getCloudfrontDomainName(distributeId));

    }

    public void deleteDistribute(String subDomain) {
        cloudfrontService.disableDitribute(subDomain);
        s3Service.delete(subDomain);
        route53Service.delete(subDomain);
    }
}
