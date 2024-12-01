package com.yoyomo.domain.application.domain.service;

import com.yoyomo.domain.application.domain.entity.Answer;
import com.yoyomo.domain.application.domain.repository.mongo.AnswerRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnswerGetService {

    private final AnswerRepository answerRepository;

    public Answer findByApplicationId(UUID applicationId) {
        return answerRepository.findByApplicationId(applicationId)
                .orElse(null);
    }
}
