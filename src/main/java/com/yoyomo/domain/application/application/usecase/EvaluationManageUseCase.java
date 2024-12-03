package com.yoyomo.domain.application.application.usecase;

import com.yoyomo.domain.application.application.dto.request.ApplicationStatusRequest;
import com.yoyomo.domain.application.application.dto.request.EvaluationMemoRequest;
import com.yoyomo.domain.application.application.dto.request.EvaluationRequest;
import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.application.domain.entity.Evaluation;
import com.yoyomo.domain.application.domain.entity.EvaluationMemo;
import com.yoyomo.domain.application.domain.service.ApplicationGetService;
import com.yoyomo.domain.application.domain.service.ApplicationUpdateService;
import com.yoyomo.domain.application.domain.service.EvaluationGetService;
import com.yoyomo.domain.application.domain.service.EvaluationMemoSaveService;
import com.yoyomo.domain.application.domain.service.EvaluationSaveService;
import com.yoyomo.domain.application.domain.service.EvaluationUpdateService;
import com.yoyomo.domain.club.domain.service.ClubManagerAuthService;
import com.yoyomo.domain.user.domain.entity.User;
import com.yoyomo.domain.user.domain.service.UserGetService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EvaluationManageUseCase {

    private final ApplicationGetService applicationGetService;
    private final ApplicationUpdateService applicationUpdateService;
    private final UserGetService userGetService;
    private final ClubManagerAuthService clubManagerAuthService;
    private final EvaluationSaveService evaluationSaveService;
    private final EvaluationGetService evaluationGetService;
    private final EvaluationUpdateService evaluationUpdateService;
    private final EvaluationMemoSaveService evaluationMemoSaveService;

    @Transactional
    public void saveRating(String applicationId, EvaluationRequest request, long userId) {
        Application application = applicationGetService.find(applicationId);
        User manager = userGetService.find(userId);
        clubManagerAuthService.checkAuthorization(application.getRecruitmentId(), manager);

        Evaluation evaluation = request.toEvaluation(manager, application);
        evaluationSaveService.save(evaluation);
    }

    @Transactional
    public void updateRating(long evaluationId, EvaluationRequest request, long userId) {
        Evaluation evaluation = evaluationGetService.find(evaluationId);
        evaluationUpdateService.update(evaluation, request.rating(), userId);
    }

    @Transactional
    public void updateStatus(String applicationId, ApplicationStatusRequest request, long userId) {
        Application application = applicationGetService.find(applicationId);
        User manager = userGetService.find(userId);
        clubManagerAuthService.checkAuthorization(application.getRecruitmentId(), manager);

        applicationUpdateService.evaluate(application, request.status());
    }

    @Transactional
    public void createMemo(String applicationId, EvaluationMemoRequest request, long userId) {
        Application application = applicationGetService.find(applicationId);
        User manager = userGetService.find(userId);
        clubManagerAuthService.checkAuthorization(application.getRecruitmentId(), manager);

        EvaluationMemo evaluationMemo = request.toEvaluationMemo(manager, application);
        evaluationMemoSaveService.save(evaluationMemo);
    }
}
