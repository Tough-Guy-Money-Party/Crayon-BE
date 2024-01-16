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
                Type.RADIO, SelectCreationStrategy.getInstance(),
                Type.CHECKBOX, SelectCreationStrategy.getInstance(),
                Type.SHORT, TextCreationStrategy.getInstance(),
                Type.LONG, TextCreationStrategy.getInstance()
        );
    }

    public Item createItem(ItemRequest request) {
        ItemCreationStrategy strategy = creationStrategies.get(request.type());
        return strategy.create(request);
    }
}
