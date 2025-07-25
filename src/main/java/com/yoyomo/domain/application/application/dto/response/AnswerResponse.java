package com.yoyomo.domain.application.application.dto.response;

import java.util.List;

import com.yoyomo.domain.application.domain.entity.Answer;
import com.yoyomo.domain.item.domain.entity.Item;

public class AnswerResponse {

	public record Response(
		String id,
		List<Item> items
	) {
		public static Response toAnswerResponse(Answer answer) {
			return new AnswerResponse.Response(
				answer.getId(),
				answer.getItems()
			);
		}
	}
}
