package com.yoyomo.domain.application.application.usecase;

import com.yoyomo.domain.application.application.dto.request.ApplicationRequestDTO;

public interface ApplyUseCase {

    void apply(ApplicationRequestDTO.Save dto, String recruitmentId);
}
