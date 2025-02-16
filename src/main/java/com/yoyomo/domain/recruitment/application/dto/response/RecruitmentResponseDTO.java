package com.yoyomo.domain.recruitment.application.dto.response;

import com.yoyomo.domain.form.application.dto.response.FormResponseDTO.Info;
import com.yoyomo.domain.recruitment.domain.entity.Recruitment;
import com.yoyomo.domain.recruitment.domain.entity.enums.Status;
import com.yoyomo.domain.recruitment.domain.entity.enums.Submit;
import com.yoyomo.domain.recruitment.domain.entity.enums.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;

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

    @Builder
    public record DetailResponse(
            String title,
            String generation,
            String position,
            String clubName,
            LocalDateTime startAt,
            Type currentProcess,
            List<ProcessResponseDTO.Response> processes,
            int processCount,
            Info form
    ) {
        public static DetailResponse toDetailResponse(Recruitment recruitment,
                                                      List<ProcessResponseDTO.Response> processes, Info form) {
            return DetailResponse.builder()
                    .title(recruitment.getTitle())
                    .generation(recruitment.getGeneration())
                    .position(recruitment.getPosition())
                    .clubName(recruitment.getClub().getName())
                    .startAt(recruitment.getStartAt())
                    .currentProcess(recruitment.getCurrentProcess())
                    .processes(processes)
                    .processCount(processes.size())
                    .form(form)
                    .build();
        }
    }
}

