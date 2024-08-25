package com.yoyomo.domain.application.application.usecase;

import com.yoyomo.domain.application.application.dto.request.EvaluationRequestDTO.Save;
import com.yoyomo.domain.application.application.mapper.EvaluationMapper;
import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.application.domain.entity.Evaluation;
import com.yoyomo.domain.application.domain.service.ApplicationGetService;
import com.yoyomo.domain.application.domain.service.EvaluationGetService;
import com.yoyomo.domain.application.domain.service.EvaluationSaveService;
import com.yoyomo.domain.application.domain.service.EvaluationUpdateService;
import com.yoyomo.domain.application.exception.AccessDeniedException;
import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.user.domain.entity.Manager;
import com.yoyomo.domain.user.domain.service.UserGetService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EvaluationManageUseCaseImpl implements EvaluationManageUseCase {

    private final EvaluationMapper evaluationMapper;
    private final ApplicationGetService applicationGetService;
    private final UserGetService userGetService;
    private final EvaluationSaveService evaluationSaveService;
    private final EvaluationGetService evaluationGetService;
    private final EvaluationUpdateService evaluationUpdateService;

    @Override @Transactional
    public void save(String applicationId, Save dto, Long userId) {
        Application application = applicationGetService.find(applicationId);
        Manager manager = userGetService.find(userId);
        Club.checkAuthority(application.getProcess().getRecruitment().getClub(), manager);

        Evaluation evaluation = evaluationSaveService.save(evaluationMapper.from(dto, manager, application));
        manager.addEvaluation(evaluation);
        application.evaluate(evaluation);
    }

    @Override
    public void update(Long evaluationId, Save dto, Long userId) {
        Evaluation evaluation = evaluationGetService.find(evaluationId);
        checkAuthority(evaluation, userId);
        evaluationUpdateService.update(evaluation, dto);
    }

    @Override
    public void delete(Long evaluationId, Long userId) {
        Evaluation evaluation = evaluationGetService.find(evaluationId);
        checkAuthority(evaluation, userId);
        evaluationUpdateService.delete(evaluation);
    }

    private void checkAuthority(Evaluation evaluation, Long userId) {
        if(!evaluation.getManager().getId().equals(userId))
            throw new AccessDeniedException();
    }

}
