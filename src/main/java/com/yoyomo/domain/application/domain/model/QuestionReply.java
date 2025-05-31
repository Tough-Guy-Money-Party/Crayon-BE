package com.yoyomo.domain.application.domain.model;

import com.yoyomo.domain.item.domain.entity.Answer;
import com.yoyomo.domain.item.domain.entity.Item;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class QuestionReply {

    private final Question question;
    private final Reply reply;

    public static QuestionReply of(Question question, Reply reply) {
        if (!reply.isEmpty() && question.isDateType()) {
            return new QuestionReply(question, reply.formatDate());
        }
        return new QuestionReply(question, reply);
    }

    public boolean isApplicantInfo() {
        return question.isApplicantInfo();
    }

    public Item toAnswer() {
        return Answer.of(question.getTitle(), reply.value());
    }

    public boolean match(String keyword) {
        return question.match(keyword);
    }

    public Reply getReply() {
        return reply;
    }
}
