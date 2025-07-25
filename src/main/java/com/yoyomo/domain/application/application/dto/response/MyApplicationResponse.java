package com.yoyomo.domain.application.application.dto.response;

import com.yoyomo.domain.application.application.dto.response.AnswerResponse.Response;
import com.yoyomo.domain.application.domain.entity.Answer;
import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.application.domain.entity.Interview;

import lombok.Builder;

@Builder
public record MyApplicationResponse(
	String id,
	ApplicantResponse user,
	AnswerResponse.Response answer,
	Interview interview
) {
	public static MyApplicationResponse toResponse(Application application, Answer answer) {
		return MyApplicationResponse.builder()
			.id(application.getId().toString())
			.user(ApplicantResponse.toResponse(application))
			.answer(Response.toAnswerResponse(answer))
			.interview(application.getInterview())
			.build();
	}
}
