package com.yoyomo.domain.application.application.usecase;

import com.yoyomo.domain.application.application.dto.request.EvaluationRequestDTO.Save;
import com.yoyomo.domain.application.application.mapper.EvaluationMapper;
import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.application.domain.entity.Evaluation;
import com.yoyomo.domain.application.domain.service.ApplicationGetService;
import com.yoyomo.domain.application.domain.service.EvaluationSaveService;
import com.yoyomo.domain.user.domain.entity.Manager;
import com.yoyomo.domain.user.domain.service.UserGetService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.yoyomo.domain.club.domain.entity.Club.checkAuthority;

@Service
@RequiredArgsConstructor
public class EvaluationManageUseCaseImpl implements EvaluationManageUseCase {

    private final EvaluationMapper evaluationMapper;
    private final ApplicationGetService applicationGetService;
    private final UserGetService userGetService;
    private final EvaluationSaveService evaluationSaveService;

    @Override @Transactional
    public void save(String applicationId, Save dto, Long userId) {
        Application application = applicationGetService.find(applicationId);
        Manager manager = userGetService.find(userId);
        checkAuthority(application.getProcess().getRecruitment().getClub(), manager);

        Evaluation evaluation = evaluationSaveService.save(evaluationMapper.from(dto, manager, application));
        manager.addEvaluation(evaluation);
        application.evaluate(evaluation);
    }

}
