package com.yoyomo.domain.application.application.usecase;

import com.yoyomo.domain.application.application.dto.request.EvaluationRequestDTO;

public interface EvaluationManageUseCase {

    void save(String applicationId, EvaluationRequestDTO.Save dto, Long userId);
}
