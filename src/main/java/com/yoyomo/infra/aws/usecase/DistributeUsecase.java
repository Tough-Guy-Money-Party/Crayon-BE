package com.yoyomo.infra.aws.usecase;

import com.yoyomo.infra.aws.cloudfront.Service.CloudfrontService;
import com.yoyomo.infra.aws.route53.service.Route53Service;
import com.yoyomo.infra.aws.s3.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DistributeUsecase {
    private final S3Service s3Service;
    private final CloudfrontService cloudfrontService;
    private final Route53Service route53Service;
    private final String BASEURL = ".crayon.land";

    public void create(String subDomain) {
        String fullSubDomain = subDomain + BASEURL;
        //버킷 생성
        s3Service.createBucket(fullSubDomain);

        // route53 레코드 생성
        createRecord(fullSubDomain);

    }

    private void createRecord(String subDomain) {
        String distributeId = cloudfrontService.create(subDomain);
        String cloudfrontDomainName = cloudfrontService.getCloudfrontDomainName(distributeId);

        route53Service.create(subDomain, cloudfrontDomainName);
    }

    public String delete(String subDomain) {
        String fullSubDomain = subDomain + BASEURL;

        //cloudfront 배포 비활성화
        cloudfrontService.disableDitribute(fullSubDomain);

        //s3버킷 삭제
        s3Service.delete(fullSubDomain);

        //route53 도메인 레코드 비활성화
        route53Service.delete(fullSubDomain);

        return subDomain;
    }

}
