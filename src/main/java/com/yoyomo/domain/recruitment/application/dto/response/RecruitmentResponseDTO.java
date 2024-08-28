package com.yoyomo.domain.recruitment.application.dto.response;

import com.yoyomo.domain.recruitment.domain.entity.enums.Status;
import com.yoyomo.domain.recruitment.domain.entity.enums.Submit;

import java.time.LocalDate;
import java.util.List;

public class RecruitmentResponseDTO {

    public record Response(
            String id,
            String title,
            String position,
            String generation,
            Submit submit,
            Status status,
            Boolean isActive,
            LocalDate recruitmentEndDate,   // 모집 마지막 프로세스의 마감 일자
            Integer totalApplicantsCount,
            String formId
    ) {}

    public record DetailResponse(
            String title,
            String generation,
            String position,
            List<ProcessResponseDTO.Response> processes,
            int processCount
    ) {}
}
