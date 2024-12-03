package com.yoyomo.domain.application.application.dto.request;

import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.application.domain.entity.EvaluationMemo;
import com.yoyomo.domain.user.domain.entity.User;
import jakarta.validation.constraints.NotNull;

public record EvaluationMemoRequest(

        @NotNull
        String memo
) {
    public EvaluationMemo toEvaluationMemo(User manager, Application application) {
        return EvaluationMemo.builder()
                .memo(memo)
                .processId(application.getProcess().getId())
                .manager(manager)
                .application(application)
                .build();
    }
}
