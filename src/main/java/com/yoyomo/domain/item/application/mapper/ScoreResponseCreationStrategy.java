package com.yoyomo.domain.item.application.mapper;

import org.springframework.stereotype.Component;

import com.yoyomo.domain.item.application.dto.res.ScoreResponse;
import com.yoyomo.domain.item.domain.entity.Item;
import com.yoyomo.domain.item.domain.entity.Score;
import com.yoyomo.domain.item.domain.entity.type.Type;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ScoreResponseCreationStrategy implements ItemResponseCreationStrategy {

	@Override
	public boolean isSupported(Type type) {
		return Type.SCORE == type;
	}

	@Override
	public ScoreResponse create(Item item) {
		return ScoreResponse.toResponse((Score)item);
	}
}
