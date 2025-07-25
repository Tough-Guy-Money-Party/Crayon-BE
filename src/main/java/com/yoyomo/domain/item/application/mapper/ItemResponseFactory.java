package com.yoyomo.domain.item.application.mapper;

import java.util.Set;

import org.springframework.stereotype.Component;

import com.yoyomo.domain.item.application.dto.res.ItemResponse;
import com.yoyomo.domain.item.domain.entity.Item;
import com.yoyomo.domain.item.domain.entity.type.Type;
import com.yoyomo.domain.item.exception.InvalidItemException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ItemResponseFactory {

	private final Set<ItemResponseCreationStrategy> creationStrategies;

	public ItemResponse createItem(Item item) {
		Type type = item.getType();
		ItemResponseCreationStrategy itemResponseCreationStrategy = creationStrategies.stream()
			.filter(strategy -> strategy.isSupported(type))
			.findFirst()
			.orElseThrow(InvalidItemException::new);
		return itemResponseCreationStrategy.create(item);
	}
}
