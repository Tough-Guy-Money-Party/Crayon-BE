package com.yoyomo.domain.item.domain.service.factory;

import com.yoyomo.domain.item.application.dto.req.ItemRequest;
import com.yoyomo.domain.item.domain.entity.Answer;
import com.yoyomo.domain.item.domain.entity.Item;
import com.yoyomo.domain.item.domain.entity.type.Type;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AnswerCreationStrategy implements ItemCreationStrategy {

    @Override
    public boolean isSupported(Type type) {
        return Type.SHORT_FORM == type || Type.LONG_FORM == type;
    }

    @Override
    public Item create(ItemRequest request) {
        return Answer.builder()
                .type(request.type())
                .title(request.title())
                .description(request.description())
                .order(request.order())
                .image(request.image())
                .required(request.required())
                .answer(request.answer())
                .maxLength(request.maxLength())
                .build();
    }
}
