package com.yoyomo.domain.landing.application.usecase;

import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.club.domain.service.ClubGetService;
import com.yoyomo.domain.club.domain.service.ClubValidateService;
import com.yoyomo.domain.landing.application.dto.request.LandingRequestDTO;
import com.yoyomo.domain.landing.application.dto.response.LandingResponseDTO;
import com.yoyomo.domain.landing.application.mapper.LandingMapper;
import com.yoyomo.domain.landing.domain.entity.Landing;
import com.yoyomo.domain.landing.domain.service.LandingGetService;
import com.yoyomo.domain.landing.domain.service.LandingUpdateService;
import com.yoyomo.domain.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LandingStyleManagementUsecase {

    private final ClubGetService clubGetService;
    private final LandingGetService landingGetService;
    private final LandingMapper landingMapper;
    private final LandingUpdateService landingUpdateService;
    private final ClubValidateService clubValidateService;

    @Transactional
    public void update(LandingRequestDTO.Style dto, User user) {
        Club club = clubGetService.find(dto.clubId());
        clubValidateService.checkOwnerAuthority(club.getId(), user);

        Landing landing = landingGetService.find(club);
        landingUpdateService.update(landing, dto);
    }

    @Transactional(readOnly = true)
    public LandingResponseDTO.Style read(String clubId, User user) {
        Club club = clubGetService.find(clubId);
        clubValidateService.checkAuthority(club.getId(), user);

        Landing landing = landingGetService.find(club);
        return landingMapper.toStyleResponse(club, landing);
    }
}
