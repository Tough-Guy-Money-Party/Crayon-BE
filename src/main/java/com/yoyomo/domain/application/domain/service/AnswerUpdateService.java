package com.yoyomo.domain.application.domain.service;

import com.yoyomo.domain.application.domain.entity.Answer;
import com.yoyomo.domain.application.domain.repository.mongo.AnswerRepository;
import com.yoyomo.domain.application.exception.ApplicationNotFoundException;
import com.yoyomo.domain.item.domain.entity.Item;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class AnswerUpdateService {

    private final AnswerRepository answerRepository;

    public void update(UUID applicationId, List<Item> items) {
        Answer answer = answerRepository.findByApplicationId(applicationId)
                .orElseThrow(ApplicationNotFoundException::new);

        answer.update(items);
    }
}
