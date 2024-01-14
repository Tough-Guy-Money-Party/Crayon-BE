package com.yoyomo.domain.item.domain.service.factory;

import com.yoyomo.domain.item.application.dto.ItemRequest;
import com.yoyomo.domain.item.domain.entity.Item;

public interface ItemCreationStrategy {
    Item create(ItemRequest type);
}
