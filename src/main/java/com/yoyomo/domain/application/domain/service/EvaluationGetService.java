package com.yoyomo.domain.application.domain.service;

import com.yoyomo.domain.application.domain.entity.Evaluation;
import com.yoyomo.domain.application.domain.repository.EvaluationRepository;
import com.yoyomo.domain.application.exception.EvaluationNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EvaluationGetService {

    private final EvaluationRepository evaluationRepository;

    public Evaluation find(Long id) {
        return evaluationRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(EvaluationNotFoundException::new);
    }

    public List<Evaluation> findAll(UUID applicationId){
        return evaluationRepository.findAllByApplicationIdAndDeletedAtIsNull(applicationId);
    }
}
