package com.yoyomo.domain.item.application.mapper;

import com.yoyomo.domain.item.application.dto.res.ItemResponse;
import com.yoyomo.domain.item.domain.entity.Item;
import com.yoyomo.domain.item.domain.entity.type.Type;

public interface ItemResponseCreationStrategy {

	boolean isSupported(Type type);

	ItemResponse create(Item item);
}
