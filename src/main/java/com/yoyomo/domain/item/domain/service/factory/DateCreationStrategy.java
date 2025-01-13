package com.yoyomo.domain.item.domain.service.factory;

import com.yoyomo.domain.item.application.dto.req.ItemRequest;
import com.yoyomo.domain.item.domain.entity.Date;
import com.yoyomo.domain.item.domain.entity.Item;
import com.yoyomo.domain.item.domain.entity.type.Type;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DateCreationStrategy implements ItemCreationStrategy {

    @Override
    public boolean isSupported(Type type) {
        return Type.CALENDAR == type;
    }

    @Override
    public Item create(ItemRequest request) {
        return Date.builder()
                .type(request.type())
                .title(request.title())
                .description(request.description())
                .order(request.order())
                .answer(request.answer())
                .required(request.required())
                .build();
    }
}
