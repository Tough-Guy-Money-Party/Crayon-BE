package com.yoyomo.domain.landing.application.usecase;

import com.yoyomo.domain.landing.application.dto.request.LandingRequestDTO;
import com.yoyomo.domain.landing.application.dto.response.LandingResponseDTO;
import com.yoyomo.domain.landing.application.dto.response.LandingResponseDTO.Style;

public interface LandingManagementUsecase {
    void update(LandingRequestDTO.NotionSave dto);

    LandingResponseDTO.General read(String clubId);

    void update(LandingRequestDTO.Style dto);
}
