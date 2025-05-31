package com.yoyomo.domain.application.domain.service;

import com.yoyomo.domain.application.application.mapper.AnswerMapper;
import com.yoyomo.domain.application.domain.entity.Answer;
import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.application.domain.model.ApplicantReply;
import com.yoyomo.domain.application.domain.repository.mongo.AnswerRepository;
import com.yoyomo.domain.item.domain.entity.Item;
import com.yoyomo.domain.item.domain.service.factory.ItemFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AnswerSaveService {

    private final AnswerMapper answerMapper;
    private final AnswerRepository answerRepository;
    private final ItemFactory itemFactory;

    public Answer save(List<Item> items, UUID applicationId) {
        Answer answer = answerMapper.from(items, applicationId);
        return answerRepository.save(answer);
    }

    public void save(List<ApplicantReply> applicantReplies, List<Application> applications) {
        List<Answer> answers = new ArrayList<>();
        for (int i = 0; i < applicantReplies.size(); i++) {
            UUID applicationId = applications.get(i).getId();
            ApplicantReply applicantReply = applicantReplies.get(i);

            Answer answer = Answer.builder()
                    .applicationId(applicationId.toString())
                    .items(itemFactory.createItem(applicantReply))
                    .build();

            answers.add(answer);
        }

        answerRepository.saveAll(answers);
    }
}
