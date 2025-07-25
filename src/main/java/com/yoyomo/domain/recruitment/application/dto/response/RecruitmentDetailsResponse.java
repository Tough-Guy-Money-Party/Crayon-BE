package com.yoyomo.domain.recruitment.application.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import com.yoyomo.domain.form.application.dto.response.FormResponse.Info;
import com.yoyomo.domain.recruitment.domain.dto.ProcessWithApplicantCount;
import com.yoyomo.domain.recruitment.domain.entity.Recruitment;
import com.yoyomo.domain.recruitment.domain.entity.enums.Type;

import lombok.Builder;

@Builder
public record RecruitmentDetailsResponse(
	String title,
	String generation,
	String position,
	String clubName,
	LocalDateTime startAt,
	Type currentProcess,
	List<ProcessResponse> processes,
	int processCount,
	Info form
) {

	public static RecruitmentDetailsResponse toRecruitmentDetailsResponse(
		Recruitment recruitment,
		List<ProcessWithApplicantCount> processWithApplicantCounts,
		Info form
	) {
		List<ProcessResponse> processes = ProcessResponse.toResponse(processWithApplicantCounts);
		return RecruitmentDetailsResponse.builder()
			.title(recruitment.getTitle())
			.generation(recruitment.getGeneration())
			.position(recruitment.getPosition())
			.clubName(recruitment.getClub().getName())
			.startAt(recruitment.getStartAt())
			.currentProcess(recruitment.getCurrentProcess())
			.processes(processes)
			.processCount(processes.size())
			.form(form)
			.build();
	}
}
