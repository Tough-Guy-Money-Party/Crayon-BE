package com.yoyomo.domain.application.domain.service;

import com.yoyomo.domain.application.domain.entity.Evaluation;
import com.yoyomo.domain.application.domain.entity.enums.Rating;
import com.yoyomo.domain.application.domain.entity.enums.Status;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class EvaluationUpdateService {

    public void update(Evaluation evaluation, Rating rating, Status status, String memo) {
        evaluation.update(rating, status, memo);
    }

    public void delete(Evaluation evaluation) {
        evaluation.delete();
    }
}
