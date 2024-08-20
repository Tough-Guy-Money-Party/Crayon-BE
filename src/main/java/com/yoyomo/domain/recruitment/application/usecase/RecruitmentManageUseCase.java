package com.yoyomo.domain.recruitment.application.usecase;

import com.yoyomo.domain.recruitment.application.dto.request.RecruitmentRequestDTO;

import static com.yoyomo.domain.recruitment.application.dto.response.RecruitmentResponseDTO.Response;

public interface RecruitmentManageUseCase {
    void save(RecruitmentRequestDTO.Save dto, Long userId);

    Response read(String recruitmentId);
}
