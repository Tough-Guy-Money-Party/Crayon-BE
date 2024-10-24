package com.yoyomo.domain.landing.application.usecase;

import com.yoyomo.domain.club.application.dto.response.ClubResponseDTO.Response;
import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.club.domain.service.ClubGetService;
import com.yoyomo.domain.club.domain.service.ClubUpdateService;
import com.yoyomo.domain.landing.application.dto.request.LandingRequestDTO;
import com.yoyomo.domain.landing.application.dto.response.LandingResponseDTO;
import com.yoyomo.domain.landing.application.mapper.LandingMapper;
import com.yoyomo.domain.landing.domain.entity.Landing;
import com.yoyomo.domain.landing.domain.service.LandingGetService;
import com.yoyomo.infra.notion.service.NotionGetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LandingManageUsecaseImpl implements LandingManagementUsecase{
    private final ClubGetService clubGetService;
    private final ClubUpdateService clubUpdateService;
    private final LandingGetService landingGetService;
    private final NotionGetService notionGetService;
    private final LandingMapper landingMapper;

    @Override
    public LandingResponseDTO.General read(String clubId) {
        Club club = clubGetService.find(clubId);
        Landing landing = landingGetService.getLanding(club);
        return landingMapper.toResponse(club,landing);
    }

    @Override
    public void update(LandingRequestDTO.NotionSave dto) {
        Club club = clubGetService.find(dto.clubId());
        String parsedNotionPage = notionGetService.notionParser(dto.notionPageLink());
        clubUpdateService.update(club, parsedNotionPage);
    }
}
