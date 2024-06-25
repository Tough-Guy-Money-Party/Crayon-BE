package com.yoyomo.domain.item.domain.service.factory;

import com.mongodb.annotations.Sealed;
import com.yoyomo.domain.item.application.dto.req.ItemRequest;
import com.yoyomo.domain.item.domain.entity.Answer;
import com.yoyomo.domain.item.domain.entity.Item;
import com.yoyomo.domain.item.domain.entity.Option;
import com.yoyomo.domain.item.domain.entity.Select;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerCreationStrategy implements ItemCreationStrategy{
    private static AnswerCreationStrategy instance;

    public static AnswerCreationStrategy getInstance() {
        if (instance == null) {
            return instance = new AnswerCreationStrategy();
        }
        return instance;
    }
    @Override
    public Item create(ItemRequest request) {
        return Answer.builder()
                .type(request.type())
                .title(request.title())
                .description(request.description())
                .order(request.order())
                .required(request.required())
                .answer(request.answer())
                .maxLength(request.maxLength())
                .build();
    }
}
