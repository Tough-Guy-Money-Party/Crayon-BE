package com.yoyomo.domain.application.domain.service;

import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.application.domain.entity.Evaluation;
import com.yoyomo.domain.application.domain.repository.EvaluationRepository;
import com.yoyomo.domain.application.exception.EvaluationNotFoundException;
import com.yoyomo.domain.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EvaluationGetService {

    private final EvaluationRepository evaluationRepository;

    public Evaluation find(Long id) {
        return evaluationRepository.findById(id)
                .orElseThrow(EvaluationNotFoundException::new);
    }

    public List<Evaluation> findAll(UUID applicationId) {
        return evaluationRepository.findAllByApplicationId(applicationId);
    }

    public List<Evaluation> findAll(Application application) {
        return evaluationRepository.findAllByApplication(application);
    }

    public Evaluation findMyEvaluation(List<Evaluation> evaluations, User manager) {
        return evaluations.stream()
                .filter(evaluation -> evaluation.getManager().equals(manager))
                .findFirst()
                .orElse(Evaluation.empty());
    }
}
