package com.yoyomo.domain.application.application.dto.response;

import static com.yoyomo.domain.application.application.dto.response.AnswerResponseDTO.Response.toAnswerResponse;

import com.yoyomo.domain.application.domain.entity.Answer;
import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.application.domain.entity.Interview;
import com.yoyomo.domain.application.domain.entity.enums.Status;
import com.yoyomo.domain.club.application.dto.response.ClubResponseDTO;
import com.yoyomo.domain.recruitment.application.dto.response.ProcessResponseDTO;
import com.yoyomo.domain.recruitment.domain.entity.enums.Type;
import com.yoyomo.domain.user.domain.entity.User;
import java.time.LocalDateTime;
import java.util.List;

public class ApplicationResponseDTO {

    public record Detail(
            String id,
            User user,
            Status status,
            Interview interview,
            boolean isBeforeInterview,
            int currentStage,
            String currentStageTitle,
            LocalDateTime createdAt,
            AnswerResponseDTO.Response answer
    ) {
        public static Detail toDetail(Application application, Answer answer, List<Type> types) {

            return new Detail(
                    application.getId().toString(),
                    application.getUser(),
                    application.getStatus(),
                    application.getInterview(),
                    application.isBeforeInterview(types),
                    application.getProcess().getStage(),
                    application.getProcess().getTitle(),
                    application.getCreatedAt(),
                    answer == null ? null : toAnswerResponse(answer)
            );
        }
    }

    public record Response(
            String id,
            ClubResponseDTO.Response club,
            List<ProcessResponseDTO.Info> processes,
            Integer currentStage
    ) {
    }

    public record MyResponse(
            String id,
            User user,
            AnswerResponseDTO.Response answer,
            Interview interview
    ) {
    }
}
