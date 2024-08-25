package com.yoyomo.domain.club.application.usecase;

import com.yoyomo.domain.club.application.dto.req.UpdateGeneralSettingsRequest;
import com.yoyomo.domain.club.application.dto.req.UpdateStyleSettingsRequest;
import com.yoyomo.domain.club.application.dto.res.ClubAllSettingsResponse;
import com.yoyomo.domain.club.application.dto.res.ClubGeneralSettingResponse;
import com.yoyomo.domain.club.application.dto.res.ClubStyleSettingsResponse;
import com.yoyomo.domain.club.application.mapper.ClubMapper;
import com.yoyomo.domain.club.application.mapper.ClubStyleMapper;
import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.club.domain.service.ClubGetService;
import com.yoyomo.domain.club.domain.service.ClubUpdateService;
import com.yoyomo.global.config.s3.RoutingDeleteService;
import com.yoyomo.global.config.s3.RoutingService;
import com.yoyomo.global.config.s3.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@Transactional
@RequiredArgsConstructor
public class LandingManageUseCaseImpl implements LandingManageUseCase {
    private final ClubUpdateService clubUpdateService;
    private final ClubGetService clubGetService;
    private final ClubMapper clubMapper;
    private final ClubStyleMapper clubStyleMapper;
    private final S3Service s3Service;
    private final RoutingService routingService;
    private final RoutingDeleteService routingDeleteService;
    private final String BASEURL = ".crayon.land";

    @Override
    public ClubGeneralSettingResponse getGeneralSetting(String email) {
        return clubMapper.clubToClubGeneralSettingResponse(clubGetService.byUserEmail(email));
    }

    @Override
    public ClubStyleSettingsResponse getStyleSetting(String email) {
        return clubStyleMapper.ClubLandingStyleToClubStyleSettingsResponse(clubGetService.byUserEmail(email).getClubLandingStyle());
    }

    @Override
    public ClubAllSettingsResponse getAllSettings(String subDomain) {
        Club club = clubGetService.bySubDomain(subDomain);
        return ClubAllSettingsResponse.of(club,club.getClubLandingStyle());
    }

    public void update(String email, UpdateGeneralSettingsRequest request) throws IOException {
        Club club = clubGetService.byUserEmail(email);

        //서브 도메인이 바뀐경우 배포 후 삭제
        updateSubdomainIfChanged(request,club);

        //노션 페이지 링크가 바뀐경우 재 배포
        if (!request.notionPageLink().equals(club.getNotionPageLink())) {
            s3Service.save(club.getSubDomain() + BASEURL, request.notionPageLink());
        }

        clubUpdateService.from(club.getId(), request);


    }

    public void update(UpdateStyleSettingsRequest request, String email) {
        Club club = clubGetService.byUserEmail(email);
        clubUpdateService.addStyle(club.getId(), clubStyleMapper.from(request));
    }

    public void create(String email, String notionPageLink) throws IOException {
        Club club = clubGetService.byUserEmail(email);
        s3Service.save(club.getSubDomain() + BASEURL, notionPageLink);
        clubUpdateService.from(club.getId(), notionPageLink);
    }

    private void updateSubdomainIfChanged(UpdateGeneralSettingsRequest request, Club club) throws IOException{
        //서브 도메인이 바뀐경우 배포 후 삭제
        if (!request.subDomain().equals(club.getSubDomain())) {
            s3Service.createBucket(request.subDomain() + BASEURL);
            routingService.handleS3Upload(request.subDomain() + BASEURL, "ap-northeast-2", request.subDomain() + BASEURL);
            s3Service.save(request.subDomain() + BASEURL,request.notionPageLink());
            routingDeleteService.deleteResources(club.getSubDomain() + BASEURL);
        }
    }
}
