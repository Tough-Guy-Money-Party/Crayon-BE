package com.yoyomo.domain.recruitment.application.dto.response;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import com.yoyomo.domain.recruitment.domain.dto.ProcessWithApplicantCount;
import com.yoyomo.domain.recruitment.domain.entity.Process;
import com.yoyomo.domain.recruitment.domain.entity.enums.ProcessStep;
import com.yoyomo.domain.recruitment.domain.entity.enums.Type;

import lombok.Builder;

@Builder
public record ProcessResponse(
	long id,
	int stage,
	Type type,
	String title,
	LocalDateTime startAt,
	LocalDateTime endAt,
	LocalDateTime announceStartAt,
	LocalDateTime announceEndAt,
	long applicantCount,
	ProcessStep processStep,
	LocalDateTime mailScheduledAt
) {

	public static List<ProcessResponse> toResponse(List<ProcessWithApplicantCount> processes) {
		return processes.stream()
			.map(ProcessResponse::toResponse)
			.sorted(Comparator.comparingInt(ProcessResponse::stage))
			.toList();
	}

	private static ProcessResponse toResponse(ProcessWithApplicantCount processWithApplicantCount) {
		Process process = processWithApplicantCount.process();
		return ProcessResponse.builder()
			.id(process.getId())
			.stage(process.getStage())
			.type(process.getType())
			.title(process.getTitle())
			.startAt(process.getStartAt())
			.endAt(process.getEndAt())
			.announceStartAt(process.getAnnounceStartAt())
			.announceEndAt(process.getAnnounceEndAt())
			.applicantCount(processWithApplicantCount.applicantCount())
			.processStep(process.getProcessStep())
			.mailScheduledAt(process.getMailScheduledAt())
			.build();
	}
}
