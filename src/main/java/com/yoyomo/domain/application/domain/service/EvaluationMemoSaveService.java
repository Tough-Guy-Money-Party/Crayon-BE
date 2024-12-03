package com.yoyomo.domain.application.domain.service;

import com.yoyomo.domain.application.domain.entity.EvaluationMemo;
import com.yoyomo.domain.application.domain.repository.EvaluationMemoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EvaluationMemoSaveService {

    private final EvaluationMemoRepository evaluationMemoRepository;

    public EvaluationMemo save(EvaluationMemo memo) {
        return evaluationMemoRepository.save(memo);
    }
}
