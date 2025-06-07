package com.yoyomo.domain.application.domain.vo;

import com.yoyomo.domain.item.domain.entity.Answer;
import com.yoyomo.domain.item.domain.entity.Item;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
@AllArgsConstructor
public class QuestionReply {

    private final Question question;
    private final Reply reply;

    public static QuestionReply of(Question question, Reply reply) {
        if (!reply.isEmpty()) {
            return new QuestionReply(question, reply);
        }

        DataType dataType = question.matchDataType();
        return new QuestionReply(question, reply.format(dataType));
    }

    public QuestionCategory getCategory() {
        return QuestionCategory.match(question);
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
