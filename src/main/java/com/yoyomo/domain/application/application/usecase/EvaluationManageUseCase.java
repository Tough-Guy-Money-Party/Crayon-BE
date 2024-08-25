package com.yoyomo.domain.application.application.usecase;

import static com.yoyomo.domain.application.application.dto.request.EvaluationRequestDTO.Save;

public interface EvaluationManageUseCase {

    void save(String applicationId, Save dto, Long userId);

    void update(Long evaluationId, Save dto, Long userId);
}
