package com.yoyomo.domain.application.domain.service;

import com.yoyomo.domain.application.domain.entity.Evaluation;
import com.yoyomo.domain.application.domain.entity.enums.Rating;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EvaluationUpdateService {

    public void update(Evaluation evaluation, Rating rating, String memo) {
        evaluation.update(rating, memo);
    }

    public void delete(Evaluation evaluation) {
        evaluation.delete();
    }
}
