package com.yoyomo.domain.application.application.dto.request;

import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.application.domain.entity.Evaluation;
import com.yoyomo.domain.application.domain.entity.enums.Rating;
import com.yoyomo.domain.application.domain.entity.enums.Status;
import com.yoyomo.domain.user.domain.entity.User;
import jakarta.validation.constraints.NotNull;

public class EvaluationRequestDTO {

    public record Save(
            @NotNull Rating rating,
            @NotNull Status status,
            String memo
    ) {
        public Evaluation toEvaluation(User manager, Application application) {
            return Evaluation.builder()
                    .rating(rating)
                    .processId(application.getProcess().getId())
                    .manager(manager)
                    .application(application)
                    .build();
        }
    }
}
