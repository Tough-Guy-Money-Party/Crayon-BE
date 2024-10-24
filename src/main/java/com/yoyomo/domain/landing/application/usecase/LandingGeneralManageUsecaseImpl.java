package com.yoyomo.domain.landing.application.usecase;

import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.club.domain.service.ClubGetService;
import com.yoyomo.domain.club.domain.service.ClubUpdateService;
import com.yoyomo.domain.club.exception.DuplicatedSubDomainException;
import com.yoyomo.domain.landing.application.dto.request.LandingRequestDTO;
import com.yoyomo.domain.landing.application.dto.request.LandingRequestDTO.General;
import com.yoyomo.domain.landing.application.dto.response.LandingResponseDTO;
import com.yoyomo.domain.landing.application.mapper.LandingMapper;
import com.yoyomo.domain.landing.domain.entity.Landing;
import com.yoyomo.domain.landing.domain.service.LandingGetService;
import com.yoyomo.domain.landing.domain.service.LandingUpdateService;
import com.yoyomo.infra.aws.s3.service.S3Service;
import com.yoyomo.infra.aws.service.AwsService;
import com.yoyomo.infra.notion.service.NotionGetService;
import jakarta.transaction.Transactional;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LandingGeneralManageUsecaseImpl implements LandingGeneralManagementUsecase {
    private final ClubGetService clubGetService;
    private final ClubUpdateService clubUpdateService;
    private final LandingGetService landingGetService;
    private final NotionGetService notionGetService;
    private final LandingMapper landingMapper;
    private final S3Service s3Service;
    private final AwsService awsService;
    private final LandingUpdateService landingUpdateService;

    private final String BASEURL = ".crayon.land";

    @Override
    public LandingResponseDTO.General readGeneral(String clubId) {
        Club club = clubGetService.find(clubId);
        Landing landing = landingGetService.getLanding(club);
        return landingMapper.toGeneralResponse(club,landing);
    }

    @Override @Transactional
    public void update(General dto) throws IOException {
        Club club = clubGetService.find(dto.clubId());
        Landing landing = landingGetService.getLanding(club);

        // 서브도메인 변경시 새 배포 시작 후 삭제
        if (isSubDomainChanged(dto,club)) {
            String subDomain = checkDuplicatedSubDomain(dto.subDomain()) + BASEURL;
            s3Service.createBucket(subDomain);
            awsService.distribute(subDomain);
            s3Service.save(subDomain);

            awsService.deleteDistribute(club.getSubDomain() + BASEURL);
        }

        landingUpdateService.update(landing, club, dto);
    }

    private String checkDuplicatedSubDomain(String subDomain) {
        if(clubGetService.checkSubDomain(subDomain))
            throw new DuplicatedSubDomainException();
        return subDomain;
    }

    private Boolean isSubDomainChanged(General dto, Club club) {
        return !dto.subDomain().equals(club.getSubDomain());
    }

    @Override
    public void update(LandingRequestDTO.NotionSave dto) {
        Club club = clubGetService.find(dto.clubId());
        String parsedNotionPage = notionGetService.notionParser(dto.notionPageLink());
        clubUpdateService.update(club, parsedNotionPage);
    }
}
