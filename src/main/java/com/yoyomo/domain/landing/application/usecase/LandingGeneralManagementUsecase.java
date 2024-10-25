package com.yoyomo.domain.landing.application.usecase;

import com.yoyomo.domain.landing.application.dto.request.LandingRequestDTO;
import com.yoyomo.domain.landing.application.dto.response.LandingResponseDTO;
import java.io.IOException;

public interface LandingGeneralManagementUsecase {
    void update(LandingRequestDTO.NotionSave dto);

    void update(LandingRequestDTO.General dto) throws IOException;

    LandingResponseDTO.General readGeneral(String clubId);

}
