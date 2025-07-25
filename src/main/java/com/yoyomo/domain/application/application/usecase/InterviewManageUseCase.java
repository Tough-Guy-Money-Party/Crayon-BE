package com.yoyomo.domain.application.application.usecase;

import org.springframework.stereotype.Service;

import com.yoyomo.domain.application.application.dto.request.InterviewRequest;
import com.yoyomo.domain.application.application.mapper.InterviewMapper;
import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.application.domain.entity.Interview;
import com.yoyomo.domain.application.domain.service.ApplicationGetService;
import com.yoyomo.domain.application.domain.service.ApplicationUpdateService;
import com.yoyomo.domain.club.domain.service.ClubManagerAuthService;
import com.yoyomo.domain.user.domain.entity.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InterviewManageUseCase {

	private final InterviewMapper interviewMapper;
	private final ApplicationUpdateService applicationUpdateService;
	private final ApplicationGetService applicationGetService;
	private final ClubManagerAuthService clubManagerAuthService;

	public void saveInterview(String applicationId, InterviewRequest.Save dto, User user) {
		Application application = checkAuthorityByApplication(applicationId, user);
		Interview interview = interviewMapper.from(dto);
		applicationUpdateService.update(application, interview);
	}

	private Application checkAuthorityByApplication(String applicationId, User manager) {
		Application application = applicationGetService.find(applicationId);
		clubManagerAuthService.checkAuthorization(application.getRecruitmentId(), manager);

		return application;
	}
}
