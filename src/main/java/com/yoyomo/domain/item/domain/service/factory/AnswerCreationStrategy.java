package com.yoyomo.domain.item.domain.service.factory;

import java.util.List;

import org.springframework.stereotype.Component;

import com.yoyomo.domain.application.domain.vo.ApplicationReply;
import com.yoyomo.domain.item.application.dto.req.ItemRequest;
import com.yoyomo.domain.item.domain.entity.Answer;
import com.yoyomo.domain.item.domain.entity.Item;
import com.yoyomo.domain.item.domain.entity.type.Type;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AnswerCreationStrategy implements ItemCreationStrategy {

	@Override
	public boolean isSupported(Type type) {
		return Type.SHORT_FORM == type || Type.LONG_FORM == type;
	}

	@Override
	public Item create(ItemRequest request) {
		return Answer.builder()
			.type(request.type())
			.title(request.title())
			.description(request.description())
			.order(request.order())
			.required(request.required())
			.answer(request.answer())
			.maxLength(request.maxLength())
			.build();
	}

	public List<Item> create(ApplicationReply applicationReply) {
		return applicationReply.toAnswers();
	}
}
