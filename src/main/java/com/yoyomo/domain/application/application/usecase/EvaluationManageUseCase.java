package com.yoyomo.domain.application.application.usecase;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yoyomo.domain.application.application.dto.request.ApplicationStatusRequest;
import com.yoyomo.domain.application.application.dto.request.EvaluationMemoRequest;
import com.yoyomo.domain.application.application.dto.request.EvaluationRequest;
import com.yoyomo.domain.application.application.dto.response.EvaluationResponses;
import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.application.domain.entity.Evaluation;
import com.yoyomo.domain.application.domain.entity.EvaluationMemo;
import com.yoyomo.domain.application.domain.entity.ProcessResult;
import com.yoyomo.domain.application.domain.service.ApplicationGetService;
import com.yoyomo.domain.application.domain.service.ApplicationUpdateService;
import com.yoyomo.domain.application.domain.service.EvaluationGetService;
import com.yoyomo.domain.application.domain.service.EvaluationMemoGetService;
import com.yoyomo.domain.application.domain.service.EvaluationMemoSaveService;
import com.yoyomo.domain.application.domain.service.EvaluationMemoUpdateService;
import com.yoyomo.domain.application.domain.service.EvaluationSaveService;
import com.yoyomo.domain.application.domain.service.EvaluationUpdateService;
import com.yoyomo.domain.application.domain.service.ProcessResultGetService;
import com.yoyomo.domain.club.domain.service.ClubManagerAuthService;
import com.yoyomo.domain.user.domain.entity.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EvaluationManageUseCase {

	private final ApplicationGetService applicationGetService;
	private final ApplicationUpdateService applicationUpdateService;
	private final ClubManagerAuthService clubManagerAuthService;
	private final EvaluationSaveService evaluationSaveService;
	private final EvaluationGetService evaluationGetService;
	private final EvaluationUpdateService evaluationUpdateService;
	private final EvaluationMemoSaveService evaluationMemoSaveService;
	private final EvaluationMemoGetService evaluationMemoGetService;
	private final ProcessResultGetService processResultGetService;
	private final EvaluationMemoUpdateService evaluationMemoUpdateService;

	@Transactional(readOnly = true)
	public EvaluationResponses findEvaluations(String applicationId, User manager) {
		Application application = applicationGetService.find(applicationId);
		clubManagerAuthService.checkAuthorization(application.getRecruitmentId(), manager);
		List<ProcessResult> processResult = processResultGetService.find(application);

		List<Evaluation> evaluations = evaluationGetService.findAll(application);
		Evaluation myEvaluation = evaluationGetService.findMyEvaluation(evaluations, manager);
		List<EvaluationMemo> memos = evaluationMemoGetService.findAll(application);
		return EvaluationResponses.toResponse(processResult, myEvaluation, evaluations, memos, manager);
	}

	@Transactional
	public void saveRating(String applicationId, EvaluationRequest request, User manager) {
		Application application = applicationGetService.find(applicationId);
		clubManagerAuthService.checkAuthorization(application.getRecruitmentId(), manager);

		Evaluation evaluation = request.toEvaluation(manager, application);
		evaluationSaveService.save(manager, evaluation);
	}

	@Transactional
	public void updateRating(long evaluationId, EvaluationRequest request, User manager) {
		Evaluation evaluation = evaluationGetService.find(evaluationId);
		evaluationUpdateService.update(evaluation, request.rating(), manager);
	}

	@Transactional
	public void updateStatus(String applicationId, ApplicationStatusRequest request, User manager) {
		Application application = applicationGetService.find(applicationId);
		clubManagerAuthService.checkAuthorization(application.getRecruitmentId(), manager);

		applicationUpdateService.evaluate(application, request.status());
	}

	@Transactional
	public void createMemo(String applicationId, EvaluationMemoRequest request, User manager) {
		Application application = applicationGetService.find(applicationId);
		clubManagerAuthService.checkAuthorization(application.getRecruitmentId(), manager);

		EvaluationMemo evaluationMemo = request.toEvaluationMemo(manager, application);
		evaluationMemoSaveService.save(evaluationMemo);
	}

	@Transactional
	public void deleteMemo(long memoId, User manager) {
		evaluationMemoUpdateService.delete(memoId, manager);
	}

	@Transactional
	public void deleteRating(long evaluationId, User manager) {
		Evaluation evaluation = evaluationGetService.find(evaluationId);

		evaluationUpdateService.delete(evaluation, manager);
	}

	@Transactional
	public void updateMemo(long memoId, EvaluationMemoRequest request, User manager) {
		evaluationMemoUpdateService.update(request.memo(), memoId, manager);
	}
}
