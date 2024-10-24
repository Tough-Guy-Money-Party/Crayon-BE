package com.yoyomo.domain.landing.application.usecase;

import com.yoyomo.domain.landing.application.dto.request.LandingRequestDTO;
import com.yoyomo.domain.landing.application.dto.response.LandingResponseDTO;

public interface LandingStyleManagementUsecase {

    LandingResponseDTO.Style read(String clubId);

    void update(LandingRequestDTO.Style dto);
}
