package com.yoyomo.domain.application.domain.service;

import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.application.domain.entity.Evaluation;
import com.yoyomo.domain.application.domain.entity.Interview;
import com.yoyomo.domain.application.domain.entity.enums.Rating;
import com.yoyomo.domain.application.domain.repository.EvaluationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ApplicationUpdateService {

    private final EvaluationRepository evaluationRepository;

    public void delete(Application application) {
        application.delete();
    }

    public void update(Application application, Interview interview) {
        application.addInterview(interview);
    }

    public void evaluate(Application application, Evaluation evaluation) {
        List<Evaluation> evaluations = evaluationRepository.findAllByApplicationIdAndDeletedAtIsNull(application.getId())
                .stream()
                .filter(e -> e.getStage() == application.getProcess().getStage())
                .toList();
        Rating rating = Rating.calculate(evaluations);

        application.evaluate(evaluation.getStatus(), rating); // todo application의 status는 뭔가요 ? evaluation과 다른가?
    }
}
