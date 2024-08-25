package com.yoyomo.domain.application.domain.service;

import com.yoyomo.domain.application.domain.entity.Evaluation;
import com.yoyomo.domain.application.domain.repository.EvaluationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EvaluationSaveService {

    private final EvaluationRepository evaluationRepository;

    public Evaluation save(Evaluation evaluation) {
        return evaluationRepository.save(evaluation);
    }
}
