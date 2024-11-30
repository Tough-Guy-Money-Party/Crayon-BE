package com.yoyomo.domain.application.application.usecase;


import com.yoyomo.domain.application.application.dto.request.InterviewRequestDTO;
import com.yoyomo.domain.application.application.mapper.InterviewMapper;
import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.application.domain.entity.Interview;
import com.yoyomo.domain.application.domain.service.ApplicationGetService;
import com.yoyomo.domain.application.domain.service.ApplicationUpdateService;
import com.yoyomo.domain.club.domain.service.ClubManagerAuthService;
import com.yoyomo.domain.user.domain.entity.User;
import com.yoyomo.domain.user.domain.service.UserGetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InterviewManageUseCase {

    private final InterviewMapper interviewMapper;
    private final ApplicationUpdateService applicationUpdateService;
    private final ApplicationGetService applicationGetService;
    private final UserGetService userGetService;
    private final ClubManagerAuthService clubManagerAuthService;

    public void saveInterview(String applicationId, InterviewRequestDTO.Save dto, Long userId) {
        Application application = checkAuthorityByApplication(applicationId, userId);
        Interview interview = interviewMapper.from(dto);
        applicationUpdateService.update(application, interview);
    }

    private Application checkAuthorityByApplication(String applicationId, Long userId) {
        Application application = applicationGetService.find(applicationId);
        User manager = userGetService.find(userId);
        clubManagerAuthService.checkAuthorization(application.getRecruitmentId(), manager);

        return application;
    }
}
