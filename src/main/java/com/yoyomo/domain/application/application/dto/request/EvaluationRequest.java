package com.yoyomo.domain.application.application.dto.request;

import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.application.domain.entity.Evaluation;
import com.yoyomo.domain.application.domain.entity.enums.Rating;
import com.yoyomo.domain.user.domain.entity.User;
import jakarta.validation.constraints.NotNull;

public record EvaluationRequest(

        @NotNull
        Rating rating
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
