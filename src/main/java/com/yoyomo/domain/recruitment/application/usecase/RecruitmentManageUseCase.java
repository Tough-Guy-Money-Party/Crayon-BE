package com.yoyomo.domain.recruitment.application.usecase;

import com.yoyomo.domain.recruitment.application.dto.request.RecruitmentRequestDTO;
import com.yoyomo.domain.recruitment.application.dto.response.RecruitmentResponseDTO.DetailResponse;
import com.yoyomo.domain.recruitment.application.dto.response.RecruitmentResponseDTO.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RecruitmentManageUseCase {
    void save(RecruitmentRequestDTO.Save dto, Long userId);

    DetailResponse read(String recruitmentId);

    Page<Response> readAll(Pageable pageable);

    void update(String recruitmentId, RecruitmentRequestDTO.Update dto, Long userId);

    void close(String recruitmentId, Long userId);

    void activate(String recruitmentId, String formId, Long userId);
}
