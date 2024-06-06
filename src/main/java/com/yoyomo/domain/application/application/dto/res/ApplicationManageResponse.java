package com.yoyomo.domain.application.application.dto.res;

import com.yoyomo.domain.application.domain.entity.Answer;
import com.yoyomo.domain.application.domain.entity.Assessment;
import com.yoyomo.domain.application.domain.entity.AssessmentRating;
import com.yoyomo.domain.application.domain.entity.SubmitStatus;
import com.yoyomo.domain.recruitment.application.dto.res.RecruitmentDetailsResponse;
import com.yoyomo.domain.user.application.dto.res.ApplicantResponse;

import java.util.List;

public record ApplicationManageResponse(
        ApplicantResponse applicant,
        RecruitmentDetailsResponse recruitment,
        List<Answer> answers,
        SubmitStatus submitStatus,
        List<ApplicantInfoDTO> currentStageApplicants,
        AssessmentRating averageRating,
        List<Assessment> assessments,
        int applicationStage
) {
}
