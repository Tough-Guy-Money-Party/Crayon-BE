package com.yoyomo.domain.item.domain.service.factory;

import com.yoyomo.domain.item.application.dto.req.ItemRequest;
import com.yoyomo.domain.item.domain.entity.Item;
import com.yoyomo.domain.item.domain.entity.type.Type;

public interface ItemCreationStrategy {

    boolean isSupported(Type type);

    Item create(ItemRequest type);
}
