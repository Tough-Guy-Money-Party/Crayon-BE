package com.yoyomo.domain.landing.application.usecase;

import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.club.domain.service.ClubGetService;
import com.yoyomo.domain.club.domain.service.ClubUpdateService;
import com.yoyomo.domain.landing.application.dto.request.LandingRequestDTO;
import com.yoyomo.infra.notion.service.NotionGetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LandingManageUsecaseImpl implements LandingManagementUsecase{
    private final ClubGetService clubGetService;
    private final ClubUpdateService clubUpdateService;
    private final NotionGetService notionGetService;

    @Override
    public void update(LandingRequestDTO.NotionSave dto) {
        Club club = clubGetService.find(dto.clubId());
        clubUpdateService.update(club, notionGetService.notionParser(dto.notionPageLink()));
    }
}
