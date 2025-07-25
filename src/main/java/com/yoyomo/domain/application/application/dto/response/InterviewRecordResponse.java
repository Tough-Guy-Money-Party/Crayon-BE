package com.yoyomo.domain.application.application.dto.response;

import lombok.Builder;

@Builder
public record InterviewRecordResponse(

	long interviewRecordId
) {

	public static InterviewRecordResponse toResponse(long interviewRecordId) {
		return InterviewRecordResponse.builder()
			.interviewRecordId(interviewRecordId)
			.build();
	}
}
