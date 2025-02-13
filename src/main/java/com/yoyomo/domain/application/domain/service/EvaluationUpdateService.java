package com.yoyomo.domain.application.domain.service;

import com.yoyomo.domain.application.domain.entity.Evaluation;
import com.yoyomo.domain.application.domain.entity.enums.Rating;
import com.yoyomo.domain.application.domain.repository.EvaluationRepository;
import com.yoyomo.domain.application.exception.AccessDeniedException;
import com.yoyomo.domain.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EvaluationUpdateService {

    private final EvaluationRepository evaluationRepository;

    public void update(Evaluation evaluation, Rating rating, User manager) {
        checkMyEvaluation(evaluation, manager);
        evaluation.update(rating);
    }

    private void checkMyEvaluation(Evaluation evaluation, User manager) {
        if (evaluation.isNotMine(manager)) {
            throw new AccessDeniedException();
        }
    }

    public void delete(Evaluation evaluation, User manager) {
        checkMyEvaluation(evaluation, manager);
        evaluationRepository.delete(evaluation);
    }
}
