package com.yoyomo.domain.application.application.dto.response;

import com.yoyomo.domain.application.domain.entity.Interview;
import com.yoyomo.domain.application.domain.entity.enums.Rating;
import com.yoyomo.domain.application.domain.entity.enums.Status;
import com.yoyomo.domain.club.application.dto.response.ClubResponseDTO;
import com.yoyomo.domain.recruitment.application.dto.response.ProcessResponseDTO;
import com.yoyomo.domain.user.domain.entity.User;

import java.time.LocalDateTime;
import java.util.List;

public class ApplicationResponseDTO {

    public record Detail(
        String id,
        User user,
        Status status,
        Rating averageRating,
        Interview interview,
        boolean isBeforeInterview,
        int currentStage,
        String currentStageTitle,
        LocalDateTime createdAt,
        AnswerResponseDTO.Response answer,
        List<EvaluationResponseDTO.Response> evaluations
    ) {}

    public record Response(
            String id,
            ClubResponseDTO.Response club,
            List<ProcessResponseDTO.Info> processes,
            Integer currentStage
    ) {}

    public record MyResponse(
            String id,
            User user,
            AnswerResponseDTO.Response answer,
            Interview interview
    ) {}
}
