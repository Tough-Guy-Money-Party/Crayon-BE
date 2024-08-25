package com.yoyomo.domain.application.domain.service;

import com.yoyomo.domain.application.domain.entity.Evaluation;
import com.yoyomo.domain.application.domain.repository.EvaluationRepository;
import com.yoyomo.domain.application.exception.EvaluationNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EvaluationGetService {

    private final EvaluationRepository evaluationRepository;

    public Evaluation find(Long id) {
        return evaluationRepository.findById(id)
                .orElseThrow(EvaluationNotFoundException::new);
    }
}
