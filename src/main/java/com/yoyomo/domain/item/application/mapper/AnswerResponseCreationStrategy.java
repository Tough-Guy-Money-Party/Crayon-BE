package com.yoyomo.domain.item.application.mapper;

import org.springframework.stereotype.Component;

import com.yoyomo.domain.item.application.dto.res.AnswerResponse;
import com.yoyomo.domain.item.domain.entity.Answer;
import com.yoyomo.domain.item.domain.entity.Item;
import com.yoyomo.domain.item.domain.entity.type.Type;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AnswerResponseCreationStrategy implements ItemResponseCreationStrategy {

	@Override
	public boolean isSupported(Type type) {
		return Type.SHORT_FORM == type || Type.LONG_FORM == type;
	}

	@Override
	public AnswerResponse create(Item item) {
		return AnswerResponse.toResponse((Answer)item);
	}
}
