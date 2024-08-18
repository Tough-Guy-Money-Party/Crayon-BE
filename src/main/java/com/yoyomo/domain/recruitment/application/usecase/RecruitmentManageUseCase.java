package com.yoyomo.domain.recruitment.application.usecase;

import com.yoyomo.domain.recruitment.application.dto.request.RecruitmentRequestDTO;

public interface RecruitmentManageUseCase {
    void save(RecruitmentRequestDTO.Save dto, Long userId);
}
