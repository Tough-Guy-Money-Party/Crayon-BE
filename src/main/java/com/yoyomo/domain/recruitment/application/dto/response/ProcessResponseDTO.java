package com.yoyomo.domain.recruitment.application.dto.response;

import com.yoyomo.domain.application.application.dto.response.ApplicationResponseDTO;
import com.yoyomo.domain.recruitment.domain.entity.Process;
import com.yoyomo.domain.recruitment.domain.entity.enums.ProcessStep;
import com.yoyomo.domain.recruitment.domain.entity.enums.Type;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

public class ProcessResponseDTO {

    @Builder
    public record Response(
            long id,
            int stage,
            Type type,
            String title,
            LocalDateTime startAt,
            LocalDateTime endAt,
            LocalDateTime announceStartAt,
            LocalDateTime announceEndAt,
            Integer applicantCount,
            ProcessStep processStep
    ) {

        public static ProcessResponseDTO.Response toResponse(Process process, long applicantCount, ProcessStep processStep) {
            return Response.builder()
                    .id(process.getId())
                    .stage(process.getStage())
                    .type(process.getType())
                    .title(process.getTitle())
                    .startAt(process.getStartAt())
                    .endAt(process.getEndAt())
                    .announceStartAt(process.getAnnounceStartAt())
                    .announceEndAt(process.getAnnounceEndAt())
                    .applicantCount((int) applicantCount)
                    .processStep(processStep)
                    .build();
        }

    }

    // 기존 Response를 DetailResponse로 변경
    public record DetailResponse(
            Integer stage,
            Type type,
            String title,
            LocalDateTime startAt,
            LocalDateTime endAt,
            LocalDateTime announceStartAt,
            LocalDateTime announceEndAt,
            List<ApplicationResponseDTO.Response> applications,
            Integer applicantCount
    ) {
    }

    public record Info(
            Integer stage,
            Type type,
            String title,
            LocalDateTime startAt,
            LocalDateTime endAt,
            LocalDateTime announceStartAt,
            LocalDateTime announceEndAt
    ) {
    }

}
