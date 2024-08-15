package com.yoyomo.domain.item.domain.service.factory;

import com.yoyomo.domain.item.application.dto.req.ItemRequest;
import com.yoyomo.domain.item.application.mapper.ItemMapper;
import com.yoyomo.domain.item.domain.entity.Date;
import com.yoyomo.domain.item.domain.entity.Item;
import org.springframework.stereotype.Service;

@Service
public class DateCreationStrategy implements ItemCreationStrategy {
    private static DateCreationStrategy instance;
    private static ItemMapper itemMapper;
    private DateCreationStrategy(ItemMapper itemMapper) {
        DateCreationStrategy.itemMapper = itemMapper;
    }

    public static DateCreationStrategy getInstance() {
        if (instance == null) {
            return instance = new DateCreationStrategy(itemMapper);
        }
        return instance;
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
