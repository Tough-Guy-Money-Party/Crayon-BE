package com.yoyomo.domain.recruitment.application.dto.request;

import java.util.List;

import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.recruitment.domain.entity.Process;
import com.yoyomo.domain.recruitment.domain.entity.Recruitment;
import com.yoyomo.domain.recruitment.domain.entity.enums.Submit;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record RecruitmentSaveRequest(
	@NotEmpty String title,
	@NotEmpty List<String> positions,
	@NotEmpty String generation,
	@NotNull Submit submit,
	@NotEmpty String clubId,
	@Valid @NotNull List<ProcessRequest.Save> processes
) {
	public List<Recruitment> toRecruitments(Club club) {
		Process firstProcess = toProcesses().get(0);
		return positions.stream()
			.map(position -> toRecruitment(club, firstProcess, position))
			.toList();
	}

	private Recruitment toRecruitment(Club club, Process process, String position) {
		return Recruitment.builder()
			.title(title)
			.generation(generation)
			.club(club)
			.submit(submit)
			.position(position)
			.startAt(process.getStartAt())
			.endAt(process.getEndAt())
			.build();
	}

	public List<Process> toProcesses() {
		return processes.stream()
			.map(ProcessRequest.Save::toProcess)
			.toList();
	}
}
