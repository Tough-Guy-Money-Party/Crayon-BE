package com.yoyomo.infra.aws.usecase;

import com.yoyomo.domain.club.exception.DuplicatedSubDomainException;
import com.yoyomo.infra.aws.cloudfront.Service.CloudfrontService;
import com.yoyomo.infra.aws.constant.ReservedSubDomain;
import com.yoyomo.infra.aws.route53.service.Route53Service;
import com.yoyomo.infra.aws.s3.service.S3Service;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DistrubuteUsecaseImpl implements DistributeUsecase {
    private final S3Service s3Service;
    private final CloudfrontService cloudfrontService;
    private final Route53Service route53Service;
    private final String BASEURL = ".crayon.land";

    @Override
    public void create(String subDomain) throws IOException {
        String fullSubDomain = subDomain + BASEURL;

        //버킷 생성
        s3Service.createBucket(fullSubDomain);

        // route53 레코드 생성
        createRecord(fullSubDomain);

        // S3에 next app 업로드
        try {
            s3Service.upload(fullSubDomain);
        } catch (Exception e) {
            delete(fullSubDomain);
            throw e;
        }

    }

    private void createRecord(String subDomain) {
        String distributeId = cloudfrontService.create(subDomain);
        String cloudfrontDomainName = cloudfrontService.getCloudfrontDomainName(distributeId);

        route53Service.create(subDomain, cloudfrontDomainName);
    }

    public void checkValidSubdomain(String subDomain) {
        String fullSubDomain = subDomain + BASEURL;
        checkReservedSubDomain(subDomain);
        cloudfrontService.validateActiveDistribution(fullSubDomain);
    }

    private void checkReservedSubDomain(String subDomain) {
        if (ReservedSubDomain.contains(subDomain)) {
            throw new DuplicatedSubDomainException();
        }
    }


    @Override
    public void delete(String subDomain) throws IOException {
        String fullSubDomain = subDomain + BASEURL;

        //cloudfront 배포 비활성화
        cloudfrontService.disableDitribute(fullSubDomain);

        //s3버킷 삭제
        s3Service.delete(fullSubDomain);

        //route53 도메인 레코드 비활성화
        route53Service.delete(fullSubDomain);
    }

}
