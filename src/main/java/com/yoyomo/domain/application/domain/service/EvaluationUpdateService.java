package com.yoyomo.domain.application.domain.service;

import com.yoyomo.domain.application.domain.entity.Evaluation;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.yoyomo.domain.application.application.dto.request.EvaluationRequestDTO.Save;

@Service
@Transactional
@RequiredArgsConstructor
public class EvaluationUpdateService {

    public void update(Evaluation evaluation, Save dto) {
        evaluation.update(dto);
    }
}
