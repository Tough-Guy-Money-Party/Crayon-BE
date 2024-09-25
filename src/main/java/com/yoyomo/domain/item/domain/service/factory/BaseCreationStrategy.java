package com.yoyomo.domain.item.domain.service.factory;

import com.yoyomo.domain.item.application.dto.req.ItemRequest;
import com.yoyomo.domain.item.domain.entity.Item;

public class BaseCreationStrategy implements ItemCreationStrategy {

    private static final BaseCreationStrategy INSTANCE = new BaseCreationStrategy();

    private BaseCreationStrategy() {
    }

    public static BaseCreationStrategy getInstance() {
        return INSTANCE;
    }

    @Override
    public Item create(ItemRequest request) {
        return Item.builder()
                .title(request.title())
                .description(request.description())
                .type(request.type())
                .order(request.order())
                .image(request.image())
                .required(request.required())
                .build();
    }

}