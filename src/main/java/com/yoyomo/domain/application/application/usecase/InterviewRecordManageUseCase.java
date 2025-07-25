package com.yoyomo.domain.application.application.usecase;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yoyomo.domain.application.application.dto.request.InterviewRecordRequest;
import com.yoyomo.domain.application.application.dto.response.InterviewRecordDetailResponse;
import com.yoyomo.domain.application.application.dto.response.InterviewRecordResponse;
import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.application.domain.entity.InterviewRecord;
import com.yoyomo.domain.application.domain.service.ApplicationGetService;
import com.yoyomo.domain.application.domain.service.InterviewRecordGetService;
import com.yoyomo.domain.application.domain.service.InterviewRecordSaveService;
import com.yoyomo.domain.application.domain.service.InterviewRecordUpdateService;
import com.yoyomo.domain.club.domain.service.ClubManagerAuthService;
import com.yoyomo.domain.user.domain.entity.User;
import com.yoyomo.domain.user.domain.service.UserGetService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InterviewRecordManageUseCase {

	private final InterviewRecordSaveService interviewRecordSaveService;
	private final UserGetService userGetService;
	private final ClubManagerAuthService clubManagerAuthService;
	private final ApplicationGetService applicationGetService;
	private final InterviewRecordGetService interviewRecordGetService;
	private final InterviewRecordUpdateService interviewRecordUpdateService;

	@Transactional
	public InterviewRecordResponse saveInterviewRecord(UUID applicationId, User manager,
		InterviewRecordRequest request) {
		Application application = applicationGetService.find(applicationId);
		clubManagerAuthService.checkAuthorization(application.getRecruitmentId(), manager);

		InterviewRecord interviewRecord = request.toInterviewRecord(manager, application.getId());
		InterviewRecord savedRecord = interviewRecordSaveService.save(interviewRecord);

		return InterviewRecordResponse.toResponse(savedRecord.getId());
	}

	@Transactional(readOnly = true)
	public List<InterviewRecordDetailResponse> readAll(UUID applicationId, User manager) {
		Application application = applicationGetService.find(applicationId);
		clubManagerAuthService.checkAuthorization(application.getRecruitmentId(), manager);

		List<InterviewRecord> interviewRecords = interviewRecordGetService.findAll(applicationId);

		return interviewRecords.stream()
			.map(record -> InterviewRecordDetailResponse.toResponse(record, manager))
			.toList();
	}

	@Transactional
	public void delete(long interviewRecordId, User manager) {
		InterviewRecord interviewRecord = interviewRecordGetService.find(interviewRecordId);
		interviewRecordUpdateService.delete(interviewRecord, manager);
	}

	@Transactional
	public void update(long interviewRecordId, User manager, InterviewRecordRequest request) {
		InterviewRecord interviewRecord = interviewRecordGetService.find(interviewRecordId);
		interviewRecordUpdateService.update(interviewRecord, manager, request.content());
	}
}
