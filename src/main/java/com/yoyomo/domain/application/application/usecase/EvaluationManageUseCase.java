package com.yoyomo.domain.application.application.usecase;

import com.yoyomo.domain.application.application.dto.request.EvaluationRequestDTO.Save;
import com.yoyomo.domain.application.application.mapper.EvaluationMapperImpl;
import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.application.domain.entity.Evaluation;
import com.yoyomo.domain.application.domain.service.ApplicationGetService;
import com.yoyomo.domain.application.domain.service.ApplicationUpdateService;
import com.yoyomo.domain.application.domain.service.EvaluationGetService;
import com.yoyomo.domain.application.domain.service.EvaluationSaveService;
import com.yoyomo.domain.application.domain.service.EvaluationUpdateService;
import com.yoyomo.domain.application.exception.AccessDeniedException;
import com.yoyomo.domain.club.domain.service.ClubManagerAuthService;
import com.yoyomo.domain.user.domain.entity.Manager;
import com.yoyomo.domain.user.domain.service.UserGetService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EvaluationManageUseCase {

    private final ApplicationGetService applicationGetService;
    private final UserGetService userGetService;
    private final EvaluationSaveService evaluationSaveService;
    private final EvaluationGetService evaluationGetService;
    private final EvaluationUpdateService evaluationUpdateService;
    private final EvaluationMapperImpl evaluationMapper;
    private final ApplicationUpdateService applicationUpdateService;
    private final ClubManagerAuthService clubManagerAuthService;

    @Transactional
    public void save(String applicationId, Save dto, long userId) {
        Application application = applicationGetService.find(applicationId);
        Manager manager = userGetService.find(userId);
        clubManagerAuthService.checkAuthorization(application.getRecruitmentId(), manager);

        Evaluation evaluation = evaluationMapper.from(dto, manager, application);
        evaluationSaveService.save(evaluation);
        applicationUpdateService.evaluate(application, dto.status());// todo application status 분리하기
    }

    @Transactional
    public void update(long evaluationId, Save dto, long userId) {
        Evaluation evaluation = evaluationGetService.find(evaluationId);
        checkMyEvaluation(evaluation, userId);

        evaluationUpdateService.update(evaluation, dto.rating(), dto.memo());
    }

    @Transactional
    public void delete(Long evaluationId, Long userId) {
        Evaluation evaluation = evaluationGetService.find(evaluationId);
        checkMyEvaluation(evaluation, userId);

        evaluationUpdateService.delete(evaluation);
    }

    private void checkMyEvaluation(Evaluation evaluation, Long userId) {
        if (!evaluation.getManager().getId().equals(userId))
            throw new AccessDeniedException();
    }
}
