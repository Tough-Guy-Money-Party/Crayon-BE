package com.yoyomo.domain.item.domain.service.factory;

import com.yoyomo.domain.item.application.dto.ItemRequest;
import com.yoyomo.domain.item.domain.entity.Item;
import com.yoyomo.domain.item.domain.entity.type.Type;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ItemFactory {
    private final Map<Type, ItemCreationStrategy> creationStrategies;

    private ItemFactory() {
        this.creationStrategies = Map.of(
                Type.RADIO, new SelectCreationStrategy(),
                Type.CHECKBOX, new SelectCreationStrategy(),
                Type.SHORT, new TextCreationStrategy(),
                Type.LONG, new TextCreationStrategy()
        );
    }

    public Item createItem(ItemRequest request) {
        ItemCreationStrategy strategy = creationStrategies.get(request.type());
        return strategy.create(request);
    }
}
