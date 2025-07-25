package com.yoyomo.domain.application.application.dto.request;

public class InterviewRequest {

	public record Save(
		String place,
		String date
	) {
	}
}
