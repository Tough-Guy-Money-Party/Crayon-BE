package com.yoyomo.domain.application.application.usecase;

import com.yoyomo.domain.application.application.dto.request.InterviewRecordRequest;
import com.yoyomo.domain.application.application.dto.response.InterviewRecordResponse;
import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.application.domain.entity.InterviewRecord;
import com.yoyomo.domain.application.domain.service.ApplicationGetService;
import com.yoyomo.domain.application.domain.service.InterviewRecordSaveService;
import com.yoyomo.domain.club.domain.service.ClubManagerAuthService;
import com.yoyomo.domain.user.domain.entity.User;
import com.yoyomo.domain.user.domain.service.UserGetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InterviewRecordManageUseCase {

    private final InterviewRecordSaveService interviewRecordSaveService;
    private final UserGetService userGetService;
    private final ClubManagerAuthService clubManagerAuthService;
    private final ApplicationGetService applicationGetService;

    @Transactional
    public InterviewRecordResponse saveInterviewRecord(UUID applicationId, long managerId, InterviewRecordRequest request) {
        Application application = applicationGetService.find(applicationId);
        User manager = userGetService.find(managerId);
        clubManagerAuthService.checkAuthorization(application.getRecruitmentId(), manager);

        InterviewRecord interviewRecord = request.toInterviewRecord(manager, application.getId());
        InterviewRecord savedRecord = interviewRecordSaveService.save(interviewRecord);

        return InterviewRecordResponse.toResponse(savedRecord.getId());
    }
}
