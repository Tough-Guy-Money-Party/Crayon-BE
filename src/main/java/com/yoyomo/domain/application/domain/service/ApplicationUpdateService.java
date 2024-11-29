package com.yoyomo.domain.application.domain.service;

import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.application.domain.entity.Evaluation;
import com.yoyomo.domain.application.domain.entity.Interview;
import com.yoyomo.domain.application.domain.entity.enums.Rating;
import com.yoyomo.domain.application.domain.entity.enums.Status;
import com.yoyomo.domain.application.domain.repository.EvaluationRepository;
import com.yoyomo.domain.recruitment.domain.entity.Recruitment;
import com.yoyomo.domain.recruitment.domain.service.RecruitmentGetService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ApplicationUpdateService {

    private final EvaluationRepository evaluationRepository;
    private final RecruitmentGetService recruitmentGetService;

    public void delete(Application application) {
        Recruitment recruitment = recruitmentGetService.find(application.getRecruitmentId());
        recruitment.minusApplicantsCount();
        application.delete();
    }

    public void update(Application application, Interview interview) {
        application.addInterview(interview);
    }

    public void evaluate(Application application, Status status) { // 평가하기
        Rating rating = calculateRatingAverage(application);

        application.evaluate(status, rating); // todo application의 status는 뭔가요 ? evaluation과 다른가?
    }

    private Rating calculateRatingAverage(Application application) {
        List<Evaluation> evaluations = evaluationRepository.findAllByApplicationIdAndDeletedAtIsNull(application.getId())
                .stream()
                .filter(evaluation -> evaluation.getProcessId() == application.getProcess().getId())
                .toList();
        return Rating.calculate(evaluations);
    }
}
