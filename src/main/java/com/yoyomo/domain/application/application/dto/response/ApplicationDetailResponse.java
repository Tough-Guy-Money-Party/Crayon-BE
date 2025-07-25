package com.yoyomo.domain.application.application.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import com.yoyomo.domain.application.domain.entity.Answer;
import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.application.domain.entity.Interview;
import com.yoyomo.domain.application.domain.entity.ProcessResult;
import com.yoyomo.domain.application.domain.entity.enums.Status;
import com.yoyomo.domain.recruitment.domain.entity.enums.Type;

public record ApplicationDetailResponse(
	String id,
	ApplicantResponse user,
	Status status,
	Interview interview,
	boolean isBeforeInterview,
	int currentStage,
	String currentStageTitle,
	LocalDateTime createdAt,
	AnswerResponse.Response answer
) {

	public static ApplicationDetailResponse toResponse(Application application, Answer answer, List<Type> types,
		ProcessResult processResult) {
		return new ApplicationDetailResponse(
			application.getId().toString(),
			ApplicantResponse.toResponse(application),
			processResult.getStatus(),
			application.getInterview(),
			application.isBeforeInterview(types),
			application.getProcess().getStage(),
			application.getProcess().getTitle(),
			application.getCreatedAt(),
			answer == null ? null : AnswerResponse.Response.toAnswerResponse(answer)
		);
	}
}
