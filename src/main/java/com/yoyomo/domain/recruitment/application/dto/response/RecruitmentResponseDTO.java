package com.yoyomo.domain.recruitment.application.dto.response;

import com.yoyomo.domain.recruitment.domain.entity.Recruitment;
import com.yoyomo.domain.recruitment.domain.entity.enums.Status;
import com.yoyomo.domain.recruitment.domain.entity.enums.Submit;
import lombok.Builder;

import java.time.LocalDate;

public class RecruitmentResponseDTO {

    @Builder
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
    ) {
        public static Response toResponse(Recruitment recruitment) {
            return Response.builder()
                    .id(recruitment.getId().toString())
                    .title(recruitment.getTitle())
                    .position(recruitment.getPosition())
                    .generation(recruitment.getGeneration())
                    .submit(recruitment.getSubmit())
                    .status(Status.getStatus(recruitment))
                    .isActive(recruitment.isActive())
                    .recruitmentEndDate(recruitment.getEndDate())
                    .totalApplicantsCount(recruitment.getTotalApplicantsCount())
                    .formId(recruitment.getFormId())
                    .build();
        }
    }
}

