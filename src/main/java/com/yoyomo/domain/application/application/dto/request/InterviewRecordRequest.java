package com.yoyomo.domain.application.application.dto.request;

import java.util.UUID;

import com.yoyomo.domain.application.domain.entity.InterviewRecord;
import com.yoyomo.domain.user.domain.entity.User;

import jakarta.validation.constraints.NotNull;

public record InterviewRecordRequest(

	@NotNull
	String content
) {
	public InterviewRecord toInterviewRecord(User manager, UUID applicationId) {
		return InterviewRecord.builder()
			.applicationId(applicationId)
			.manager(manager)
			.content(content)
			.build();
	}
}
