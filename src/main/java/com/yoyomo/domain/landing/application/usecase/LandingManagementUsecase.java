package com.yoyomo.domain.landing.application.usecase;

import com.yoyomo.domain.landing.application.dto.request.LandingRequestDTO;

public interface LandingManagementUsecase {
    void update(LandingRequestDTO.NotionSave dto);
}
