package com.yoyomo.domain.application.application.dto.res;

import com.yoyomo.domain.application.domain.entity.Answer;
import com.yoyomo.domain.application.domain.entity.ApplicationStatus;
import com.yoyomo.domain.application.domain.entity.SubmitStatus;
import com.yoyomo.domain.recruitment.application.dto.res.RecruitmentDetailsResponse;
import com.yoyomo.domain.user.domain.entity.Manager;

import java.util.List;

public record ApplicationManageResponse(
        Manager manager,
        RecruitmentDetailsResponse recruitment,
        List<Answer> answers,
        SubmitStatus submitStatus,
        ApplicationStatus applicationStatus
) {
}
