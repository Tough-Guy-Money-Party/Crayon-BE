package com.yoyomo.domain.application.application.dto.response;

import com.yoyomo.domain.application.domain.entity.Answer;
import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.application.domain.entity.Interview;
import com.yoyomo.domain.application.domain.entity.enums.Rating;
import com.yoyomo.domain.application.domain.entity.enums.Status;
import com.yoyomo.domain.club.application.dto.response.ClubResponseDTO;
import com.yoyomo.domain.recruitment.application.dto.response.ProcessResponseDTO;
import com.yoyomo.domain.recruitment.domain.entity.Process;
import com.yoyomo.domain.recruitment.domain.entity.enums.Type;
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
    ) {
        public static Detail toDetail(Application application, Answer answer,
                                      List<EvaluationResponseDTO.Response> evaluations) {
            if (answer == null) {
                return null;
            }

            return new Detail(
                    application.getId().toString(),
                    application.getUser(),
                    application.getStatus(),
                    application.getAverageRating(),
                    application.getInterview(),
                    isBeforeInterview(application),
                    application.getProcess().getStage(),
                    application.getProcess().getTitle(),
                    application.getCreatedAt(),
                    toAnswerResponse(answer),
                    evaluations
            );
        }

        private static boolean isBeforeInterview(Application application) {
            List<Type> types = application.getProcess().getRecruitment().getProcesses().stream()
                    .map(Process::getType)
                    .toList();

            if (!types.contains(Type.INTERVIEW)) {
                return false;
            }

            return types.indexOf(Type.INTERVIEW) > application.getProcess().getStage();
        }

        private static AnswerResponseDTO.Response toAnswerResponse(Answer answer) {
            return new AnswerResponseDTO.Response(
                    answer.getId(),
                    answer.getItems()
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
