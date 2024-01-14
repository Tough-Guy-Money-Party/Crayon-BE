package com.yoyomo.domain.item.domain.service.factory;

import com.yoyomo.domain.item.application.dto.req.ItemRequest;
import com.yoyomo.domain.item.domain.entity.Item;
import com.yoyomo.domain.item.domain.entity.Select;
import org.springframework.stereotype.Service;

@Service
public class SelectCreationStrategy implements ItemCreationStrategy {

    @Override
    public Item create(ItemRequest request) {
        return Select.builder()
                .type(request.type())
                .question(request.question())
                .order(request.order())
                .required(request.required())
                .options(request.options())
                .build();
    }
}
