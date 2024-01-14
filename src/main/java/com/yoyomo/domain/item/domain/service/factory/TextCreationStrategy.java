package com.yoyomo.domain.item.domain.service.factory;

import com.yoyomo.domain.item.application.dto.ItemRequest;
import com.yoyomo.domain.item.domain.entity.Item;
import com.yoyomo.domain.item.domain.entity.Text;
import org.springframework.stereotype.Service;

@Service
public class TextCreationStrategy implements ItemCreationStrategy {
    @Override
    public Item create(ItemRequest request) {
        return Text.builder()
                .type(request.type())
                .question(request.question())
                .order(request.order())
                .required(request.required())
                .limit(request.limit())
                .build();
    }
}
