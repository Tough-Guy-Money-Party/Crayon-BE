package com.yoyomo.domain.application.domain.service;

import com.yoyomo.domain.application.application.mapper.AnswerMapper;
import com.yoyomo.domain.application.domain.entity.Answer;
import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.application.domain.repository.mongo.AnswerRepository;
import com.yoyomo.domain.item.domain.entity.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnswerSaveService {

    private final AnswerRepository answerRepository;
    private final AnswerMapper answerMapper;

    public Answer save(List<Item> items, Application application) {
        return answerRepository.save(answerMapper.from(items, application.getId().toString()));
    }
}
