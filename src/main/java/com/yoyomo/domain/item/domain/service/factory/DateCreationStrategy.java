package com.yoyomo.domain.item.domain.service.factory;

import com.yoyomo.domain.item.application.dto.req.ItemRequest;
import com.yoyomo.domain.item.domain.entity.Item;
import com.yoyomo.domain.item.domain.entity.Select;
import org.springframework.stereotype.Service;

@Service
public class DateCreationStrategy implements ItemCreationStrategy {
    private static DateCreationStrategy instance;

    private DateCreationStrategy() {
    }

    public static DateCreationStrategy getInstance() {
        if (instance == null) {
            return instance = new DateCreationStrategy();
        }
        return instance;
    }

    @Override
    public Item create(ItemRequest request) {
        return Select.builder()
                .type(request.type())
                .question(request.question())
                .order(request.order())
                .required(request.required())
                .build();
    }
}
