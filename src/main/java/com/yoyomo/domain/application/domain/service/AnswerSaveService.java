package com.yoyomo.domain.application.domain.service;

import com.yoyomo.domain.application.application.mapper.AnswerMapper;
import com.yoyomo.domain.application.domain.entity.Answer;
import com.yoyomo.domain.application.domain.repository.mongo.AnswerRepository;
import com.yoyomo.domain.item.domain.entity.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AnswerSaveService {

    private final AnswerMapper answerMapper;
    private final AnswerRepository answerRepository;

    public Answer save(List<Item> items, UUID applicationId) {
        Answer from = answerMapper.from(items, applicationId);
        return answerRepository.save(from);
    }
}
