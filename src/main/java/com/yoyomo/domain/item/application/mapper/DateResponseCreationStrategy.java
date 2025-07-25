package com.yoyomo.domain.item.application.mapper;

import org.springframework.stereotype.Component;

import com.yoyomo.domain.item.application.dto.res.DateResponse;
import com.yoyomo.domain.item.domain.entity.Date;
import com.yoyomo.domain.item.domain.entity.Item;
import com.yoyomo.domain.item.domain.entity.type.Type;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DateResponseCreationStrategy implements ItemResponseCreationStrategy {

	@Override
	public boolean isSupported(Type type) {
		return Type.CALENDAR == type;
	}

	@Override
	public DateResponse create(Item item) {
		return DateResponse.toResponse((Date)item);
	}
}
