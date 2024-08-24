package com.yoyomo.domain.application.domain.service;

import com.yoyomo.domain.application.domain.entity.Answer;
import com.yoyomo.domain.application.domain.repository.AnswerRepository;
import com.yoyomo.domain.application.exception.AnswerNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnswerGetService {

    private final AnswerRepository answerRepository;

    public Answer find(String id) {
        return answerRepository.findById(id)
                .orElseThrow(AnswerNotFoundException::new);
    }
}
