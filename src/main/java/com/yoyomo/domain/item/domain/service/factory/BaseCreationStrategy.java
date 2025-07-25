package com.yoyomo.domain.item.domain.service.factory;

import org.springframework.stereotype.Component;

import com.yoyomo.domain.item.application.dto.req.ItemRequest;
import com.yoyomo.domain.item.domain.entity.Item;
import com.yoyomo.domain.item.domain.entity.type.Type;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BaseCreationStrategy implements ItemCreationStrategy {

	@Override
	public boolean isSupported(Type type) {
		return Type.ANNOUNCE == type;
	}

	@Override
	public Item create(ItemRequest request) {
		return Item.builder()
			.title(request.title())
			.description(request.description())
			.type(request.type())
			.order(request.order())
			.required(request.required())
			.build();
	}
}
