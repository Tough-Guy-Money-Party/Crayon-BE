package com.yoyomo.domain.landing.application.usecase;

import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.club.domain.service.ClubGetService;
import com.yoyomo.domain.landing.application.dto.request.LandingRequestDTO;
import com.yoyomo.domain.landing.application.dto.response.LandingResponseDTO;
import com.yoyomo.domain.landing.application.mapper.LandingMapper;
import com.yoyomo.domain.landing.domain.entity.Landing;
import com.yoyomo.domain.landing.domain.service.LandingGetService;
import com.yoyomo.domain.landing.domain.service.LandingUpdateService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LandingStyleManagementUsecaseImpl implements LandingStyleManagementUsecase {
    
    private final ClubGetService clubGetService;
    private final LandingGetService landingGetService;
    private final LandingMapper landingMapper;
    private final LandingUpdateService landingUpdateService;

    @Override
    @Transactional
    public void update(LandingRequestDTO.Style dto) {
        Club club = clubGetService.find(dto.clubId());
        Landing landing = landingGetService.find(club);
        landingUpdateService.update(landing, dto);
    }

    @Override
    public LandingResponseDTO.Style read(String clubId) {
        Club club = clubGetService.find(clubId);
        Landing landing = landingGetService.find(club);
        return landingMapper.toStyleResponse(club, landing);
    }
}
