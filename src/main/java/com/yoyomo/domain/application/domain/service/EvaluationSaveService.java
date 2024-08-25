package com.yoyomo.domain.application.domain.service;

import com.yoyomo.domain.application.application.dto.request.EvaluationRequestDTO;
import com.yoyomo.domain.application.application.mapper.EvaluationMapper;
import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.application.domain.entity.Evaluation;
import com.yoyomo.domain.application.domain.repository.EvaluationRepository;
import com.yoyomo.domain.user.domain.entity.Manager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.yoyomo.domain.application.application.dto.request.EvaluationRequestDTO.*;

@Service
@RequiredArgsConstructor
public class EvaluationSaveService {

    private final EvaluationRepository evaluationRepository;
    private final EvaluationMapper evaluationMapper;

    public Evaluation save(Save dto, Manager manager, Application application) {
        return evaluationRepository.save(evaluationMapper.from(dto, manager, application));
    }
}
