package com.yoyomo.domain.recruitment.application.dto.response;

import java.time.LocalDateTime;

import com.yoyomo.domain.recruitment.domain.entity.enums.Type;

public class ProcessInfoResponse {
	public record Info(
		Integer stage,
		Type type,
		String title,
		LocalDateTime startAt,
		LocalDateTime endAt,
		LocalDateTime announceStartAt,
		LocalDateTime announceEndAt
	) {
	}
}
