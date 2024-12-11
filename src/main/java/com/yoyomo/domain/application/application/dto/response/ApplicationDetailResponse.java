package com.yoyomo.domain.application.application.dto.response;

import com.yoyomo.domain.application.domain.entity.Answer;
import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.application.domain.entity.Interview;
import com.yoyomo.domain.application.domain.entity.enums.Status;
import com.yoyomo.domain.recruitment.domain.entity.enums.Type;

import java.time.LocalDateTime;
import java.util.List;

public record ApplicationDetailResponse(
        String id,
        ApplicantResponse user,
        Status status,
        Interview interview,
        boolean isBeforeInterview,
        int currentStage,
        String currentStageTitle,
        LocalDateTime createdAt,
        AnswerResponseDTO.Response answer
) {

    public static ApplicationDetailResponse toResponse(Application application, Answer answer, List<Type> types) {
        return new ApplicationDetailResponse(
                application.getId().toString(),
                ApplicantResponse.toResponse(application),
                application.getStatus(),
                application.getInterview(),
                application.isBeforeInterview(types),
                application.getProcess().getStage(),
                application.getProcess().getTitle(),
                application.getCreatedAt(),
                answer == null ? null : AnswerResponseDTO.Response.toAnswerResponse(answer)
        );
    }
}
