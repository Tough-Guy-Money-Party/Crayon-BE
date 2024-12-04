package com.yoyomo.domain.application.domain.service;

import com.yoyomo.domain.application.domain.entity.Evaluation;
import com.yoyomo.domain.application.domain.repository.EvaluationRepository;
import com.yoyomo.domain.application.exception.EvaluationAlreadyExistException;
import com.yoyomo.domain.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EvaluationSaveService {

    private final EvaluationRepository evaluationRepository;

    public Evaluation save(User manager, Evaluation evaluation) {
        if (evaluationRepository.existsByManager(manager)) {
            throw new EvaluationAlreadyExistException();
        }
        return evaluationRepository.save(evaluation);
    }
}
