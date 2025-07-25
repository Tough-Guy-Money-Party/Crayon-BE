package com.yoyomo.domain.item.application.mapper;

import org.springframework.stereotype.Component;

import com.yoyomo.domain.item.application.dto.res.ItemResponse;
import com.yoyomo.domain.item.domain.entity.Item;
import com.yoyomo.domain.item.domain.entity.type.Type;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BaseResponseCreationStrategy implements ItemResponseCreationStrategy {

	@Override
	public boolean isSupported(Type type) {
		return Type.ANNOUNCE == type;
	}

	@Override
	public ItemResponse create(Item item) {
		return ItemResponse.toResponse(item);
	}
}
