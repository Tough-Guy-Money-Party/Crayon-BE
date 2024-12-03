package com.yoyomo.domain.application.domain.service;

import com.yoyomo.domain.application.domain.entity.Answer;
import com.yoyomo.domain.application.domain.repository.mongo.AnswerRepository;
import com.yoyomo.domain.application.exception.AnswerNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnswerGetService {

    private final AnswerRepository answerRepository;

    public Answer findByApplicationId(UUID applicationId) {
        return answerRepository.findByApplicationId(applicationId)
                .orElseThrow(AnswerNotFoundException::new);
    }

    public List<Answer> findAllByApplicationIds(List<UUID> applicationIds) {
        return answerRepository.findAllByApplicationIds(applicationIds);
    }

    public Map<UUID, Answer> findAllApplicationMapByApplicationIds(List<UUID> applicationIds) {
        return findAllByApplicationIds(applicationIds).stream()
                .collect(Collectors.toMap(Answer::getApplicationId, Function.identity()));
    }
}
