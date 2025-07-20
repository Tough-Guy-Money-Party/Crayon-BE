package com.yoyomo.domain.recruitment.application.usecase;

import static com.yoyomo.domain.form.application.dto.response.FormResponseDTO.*;
import static com.yoyomo.domain.recruitment.application.dto.response.RecruitmentResponseDTO.*;
import static com.yoyomo.domain.recruitment.application.dto.response.RecruitmentResponseDTO.Response;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yoyomo.domain.application.domain.service.ApplicationDeleteService;
import com.yoyomo.domain.application.domain.service.EvaluationDeleteService;
import com.yoyomo.domain.application.domain.service.EvaluationMemoDeleteService;
import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.club.domain.service.ClubGetService;
import com.yoyomo.domain.club.domain.service.ClubManagerAuthService;
import com.yoyomo.domain.form.application.usecase.FormManageUseCase;
import com.yoyomo.domain.form.domain.entity.Form;
import com.yoyomo.domain.form.domain.service.FormGetService;
import com.yoyomo.domain.recruitment.application.dto.request.RecruitmentSaveRequest;
import com.yoyomo.domain.recruitment.application.dto.request.RecruitmentUpdateRequest;
import com.yoyomo.domain.recruitment.application.dto.response.RecruitmentDetailsResponse;
import com.yoyomo.domain.recruitment.domain.dto.ProcessWithApplicantCount;
import com.yoyomo.domain.recruitment.domain.dto.RecruitmentCreateResponse;
import com.yoyomo.domain.recruitment.domain.entity.Process;
import com.yoyomo.domain.recruitment.domain.entity.Recruitment;
import com.yoyomo.domain.recruitment.domain.service.ProcessDeleteService;
import com.yoyomo.domain.recruitment.domain.service.ProcessGetService;
import com.yoyomo.domain.recruitment.domain.service.ProcessSaveService;
import com.yoyomo.domain.recruitment.domain.service.RecruitmentDeleteService;
import com.yoyomo.domain.recruitment.domain.service.RecruitmentGetService;
import com.yoyomo.domain.recruitment.domain.service.RecruitmentSaveService;
import com.yoyomo.domain.recruitment.domain.service.RecruitmentUpdateService;
import com.yoyomo.domain.recruitment.exception.RecruitmentDeletedException;
import com.yoyomo.domain.user.domain.entity.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecruitmentManageUseCase {

	private final ClubGetService clubGetService;

	private final RecruitmentGetService recruitmentGetService;
	private final RecruitmentSaveService recruitmentSaveService;
	private final RecruitmentUpdateService recruitmentUpdateService;
	private final RecruitmentDeleteService recruitmentDeleteService;

	private final FormGetService formGetService;
	private final FormManageUseCase formManageUseCase;
	private final ClubManagerAuthService clubManagerAuthService;

	private final ApplicationDeleteService applicationDeleteService;
	private final ProcessSaveService processSaveService;
	private final ProcessGetService processGetService;
	private final ProcessDeleteService processDeleteService;
	private final EvaluationDeleteService evaluationDeleteService;
	private final EvaluationMemoDeleteService evaluationMemoDeleteService;

	@Transactional
	public RecruitmentCreateResponse save(RecruitmentSaveRequest request, User manager) {
		Club club = clubGetService.find(request.clubId());
		clubManagerAuthService.checkAuthorization(club, manager);

		List<Recruitment> recruitments = recruitmentSaveService.saveAll(request.toRecruitments(club));
		processSaveService.saveAll(request.toProcesses(), recruitments);

		return RecruitmentCreateResponse.from(recruitments);
	}

	@Transactional(readOnly = true)
	public RecruitmentDetailsResponse read(UUID recruitmentId) {
		Recruitment recruitment = recruitmentGetService.find(recruitmentId);
		List<ProcessWithApplicantCount> processWithApplicantCounts = processGetService.findAllWithApplicantCount(
			recruitment.getId());
		Info form = formManageUseCase.readForm(recruitment.getFormId());

		return RecruitmentDetailsResponse.toRecruitmentDetailsResponse(recruitment, processWithApplicantCounts, form);
	}

	@Transactional(readOnly = true)
	public Page<Response> readAll(Pageable pageable, String clubId) {
		Club club = clubGetService.find(clubId);
		return recruitmentGetService.findAll(club, pageable)
			.map(Response::toResponse);
	}

	@Transactional
	public void update(String recruitmentId, RecruitmentUpdateRequest request, User user) {
		Recruitment recruitment = checkAuthorityByRecruitment(recruitmentId, user);
		recruitment.checkModifiable();
		recruitment.update(request.title(), request.position(), request.startAt(), request.endAt());
	}

	@Transactional
	public void close(String recruitmentId, User user) {
		Recruitment recruitment = checkAuthorityByRecruitment(recruitmentId, user);
		recruitmentUpdateService.delete(recruitment);
	}

	@Transactional
	public void activate(String recruitmentId, String formId, User user) {
		checkDeletedRecruitment(recruitmentId);
		Recruitment recruitment = checkAuthorityByRecruitment(recruitmentId, user);

		Form form = formGetService.find(formId);
		recruitmentUpdateService.update(recruitment, form.getId());
	}

	@Transactional
	public void cancel(String recruitmentId, User user) {
		Recruitment recruitment = checkAuthorityByRecruitment(recruitmentId, user);

		evaluationMemoDeleteService.deleteByRecruitmentId(recruitment.getId());
		evaluationDeleteService.deleteEvaluationByRecruitmentId(recruitment.getId());

		applicationDeleteService.deleteByRecruitmentId(recruitment);

		processDeleteService.deleteAllByRecruitment(recruitment);

		recruitmentDeleteService.delete(recruitment);
	}

	private Recruitment checkAuthorityByRecruitment(String recruitmentId, User manager) {
		Recruitment recruitment = recruitmentGetService.find(recruitmentId);
		clubManagerAuthService.checkAuthorization(recruitment.getId(), manager);

		return recruitment;
	}

	private void checkDeletedRecruitment(String recruitmentId) {
		Recruitment recruitment = recruitmentGetService.find(recruitmentId);
		if (recruitment.getDeletedAt() != null) {
			throw new RecruitmentDeletedException();
		}
	}

	@Transactional
	public void replicate(String recruitmentId, User user) {
		Recruitment recruitment = checkAuthorityByRecruitment(recruitmentId, user);
		Recruitment newRecruitment = Recruitment.replicate(recruitment);

		List<Process> newProcesses = recruitment.getProcesses()
			.stream()
			.map(Process::replicate)
			.toList();

		newProcesses.forEach(process -> process.addRecruitment(newRecruitment));
		newRecruitment.addNewProcesses(newProcesses);

		processSaveService.saveAll(newProcesses);
		recruitmentSaveService.save(newRecruitment);
	}
}
