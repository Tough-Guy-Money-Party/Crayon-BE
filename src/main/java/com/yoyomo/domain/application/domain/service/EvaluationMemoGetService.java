package com.yoyomo.domain.application.domain.service;

import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.application.domain.entity.EvaluationMemo;
import com.yoyomo.domain.application.domain.repository.EvaluationMemoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EvaluationMemoGetService {

    private final EvaluationMemoRepository evaluationMemoRepository;

    public List<EvaluationMemo> findAll(Application application) {
        return evaluationMemoRepository.findAllByApplication(application);
    }
}
