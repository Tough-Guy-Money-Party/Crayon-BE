package com.yoyomo.domain.landing.domain.service;

import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.club.domain.service.ClubUpdateService;
import com.yoyomo.domain.landing.application.dto.request.LandingRequestDTO.General;
import com.yoyomo.domain.landing.application.dto.request.LandingRequestDTO.Style;
import com.yoyomo.domain.landing.domain.entity.Landing;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LandingUpdateService {
    private final ClubUpdateService clubUpdateService;

    public void update(Landing landing, Style dto) {
        landing.updateStyle(dto);
    }

    public void update(Landing landing, Club club, General dto) {
        landing.updateGeneral(dto);
        clubUpdateService.update(club, dto);
    }
}
