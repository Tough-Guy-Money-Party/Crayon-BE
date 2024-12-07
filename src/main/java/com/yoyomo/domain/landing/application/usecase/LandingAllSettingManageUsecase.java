package com.yoyomo.domain.landing.application.usecase;

import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.club.domain.service.ClubGetService;
import com.yoyomo.domain.landing.application.dto.response.LandingResponseDTO.All;
import com.yoyomo.domain.landing.domain.entity.Landing;
import com.yoyomo.infra.notion.service.NotionGetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LandingAllSettingManageUsecase {

    private final ClubGetService clubGetService;
    private final NotionGetService notionGetService;

    public All readAll(String subDomain) {
        Club club = clubGetService.findBySubDomain(subDomain);
        Landing landing = club.getLanding();
        String parsedNotionPageLink = notionGetService.parseNotionPageLink(club.getNotionPageLink());

        return All.toAll(landing, parsedNotionPageLink);
    }
}
