package com.yoyomo.domain.application.domain.service;

import com.yoyomo.domain.application.domain.entity.Evaluation;
import com.yoyomo.domain.application.domain.entity.enums.Rating;
import com.yoyomo.domain.application.domain.repository.EvaluationRepository;
import com.yoyomo.domain.application.exception.AccessDeniedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EvaluationUpdateService {

    private final EvaluationRepository evaluationRepository;

    public void update(Evaluation evaluation, Rating rating, long userId) {
        checkMyEvaluation(evaluation, userId);
        evaluation.update(rating);
    }

    private void checkMyEvaluation(Evaluation evaluation, Long userId) {
        if (!evaluation.getManager().getId().equals(userId)) {
            throw new AccessDeniedException();
        }
    }

    public void delete(Evaluation evaluation, long managerId) {
        checkMyEvaluation(evaluation, managerId);
        evaluationRepository.delete(evaluation);
    }
}
