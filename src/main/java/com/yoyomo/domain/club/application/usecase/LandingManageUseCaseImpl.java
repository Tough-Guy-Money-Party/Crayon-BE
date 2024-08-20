package com.yoyomo.domain.club.application.usecase;

import com.yoyomo.domain.club.application.dto.req.UpdateGeneralSettingsRequest;
import com.yoyomo.domain.club.application.dto.req.UpdateStyleSettingsRequest;
import com.yoyomo.domain.club.application.dto.res.ClubGeneralSettingResponse;
import com.yoyomo.domain.club.application.dto.res.ClubStyleSettingsResponse;
import com.yoyomo.domain.club.application.mapper.ClubMapper;
import com.yoyomo.domain.club.application.mapper.ClubStyleMapper;
import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.club.domain.service.ClubGetService;
import com.yoyomo.domain.club.domain.service.ClubUpdateService;
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
public class LandingManageUseCaseImpl implements LandingManageUseCase{
    private final ClubUpdateService clubUpdateService;
    private final ClubGetService clubGetService;
    private final ClubMapper clubMapper;
    private final ClubStyleMapper clubStyleMapper;
    private final S3Service s3Service;
    private final RoutingService routingService;
    private final String BASEURL = ".crayon.land";

    @Override
    public ClubGeneralSettingResponse getGeneralSetting(String email) {
        return clubMapper.clubToClubGeneralSettingResponse(clubGetService.byUserEmail(email));
    }

    @Override
    public ClubStyleSettingsResponse getStyleSetting(String email){
        return clubStyleMapper.ClubLandingStyleToClubStyleSettingsResponse(clubGetService.byUserEmail(email).getClubLandingStyle());
    }

    public void update(String email, UpdateGeneralSettingsRequest request) throws IOException  {
        String subdomain = request.subDomain() + BASEURL;
        Club club = clubGetService.byUserEmail(email);
        clubUpdateService.from(club.getId(), request);
        routingService.handleS3Upload(subdomain,"ap-northeast-2",subdomain);
        s3Service.save(subdomain, request.notionPageLink());
    }

    public void update(UpdateStyleSettingsRequest request, String email) {
        Club club = clubGetService.byUserEmail(email);
        clubUpdateService.addStyle(club.getId(),clubStyleMapper.from(request));
    }

    public void create(String email, String notionPageLink){
        Club club = clubGetService.byUserEmail(email);
        clubUpdateService.from(club.getId(),notionPageLink);
    }
}
