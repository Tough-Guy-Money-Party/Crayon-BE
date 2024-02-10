package com.yoyomo.domain.application.application.dto.res;

import com.yoyomo.domain.application.domain.entity.Answer;
import com.yoyomo.domain.application.domain.entity.ApplicationStatus;
import com.yoyomo.domain.application.domain.entity.SubmitStatus;
import com.yoyomo.domain.recruitment.application.dto.res.RecruitmentDetailsResponse;
import com.yoyomo.domain.user.domain.entity.User;

import java.util.List;

public record ApplicationManageResponse(
        User user,
        RecruitmentDetailsResponse recruitment,
        List<Answer> answers,
        SubmitStatus submitStatus,
        ApplicationStatus applicationStatus,
        String process
) {
}
