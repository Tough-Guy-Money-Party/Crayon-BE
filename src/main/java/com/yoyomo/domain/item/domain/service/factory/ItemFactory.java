package com.yoyomo.domain.item.domain.service.factory;

import com.yoyomo.domain.item.application.dto.req.ItemRequest;
import com.yoyomo.domain.item.domain.entity.Item;
import com.yoyomo.domain.item.domain.entity.type.Type;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ItemFactory {
    private final Map<Type, ItemCreationStrategy> creationStrategies;

    private ItemFactory() {
        this.creationStrategies = Map.of(
                Type.SHORT_FORM, AnswerCreationStrategy.getInstance(),
                Type.LONG_FORM, AnswerCreationStrategy.getInstance(),
                Type.SELECT, SelectCreationStrategy.getInstance(),
                Type.MULTI_SELECT, SelectCreationStrategy.getInstance(),
                Type.CALENDAR, DateCreationStrategy.getInstance(),
                Type.SCORE, ScoreCreationStrategy.getInstance(),
                Type.ANNOUNCE, FileCreationStrategy.getInstance()
        );
    }

    public Item createItem(ItemRequest request) {
        ItemCreationStrategy strategy = creationStrategies.get(request.type());
        return strategy.create(request);
    }
}