package com.yoyomo.domain.landing.application.usecase;

import com.yoyomo.domain.landing.application.dto.request.LandingRequestDTO;
import com.yoyomo.domain.landing.application.dto.response.LandingResponseDTO;

public interface LandingManagementUsecase {
    void update(LandingRequestDTO.NotionSave dto);

    LandingResponseDTO.General readGeneral(String clubId);

    LandingResponseDTO.Style readStyle(String clubId);

    void update(LandingRequestDTO.Style dto);
}
