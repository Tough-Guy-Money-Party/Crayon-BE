package com.yoyomo.domain.application.application.dto.response;

import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.application.domain.entity.enums.Status;
import com.yoyomo.domain.recruitment.domain.entity.Recruitment;

public class ResultResponseDTO {

    public record Result(
            Status result
    ) {
        public static Result toResponse(Application application) {
            Recruitment recruitment = application.getProcess().getRecruitment();

            if (recruitment.isLastProcess(application.getProcess())) {
                return new Result(application.getStatus());
            }
            return new Result(Status.PENDING);
        }
    }
}
