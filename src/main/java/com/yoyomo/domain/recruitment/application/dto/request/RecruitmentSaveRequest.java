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
	@NotEmpty String position,
	@NotEmpty String generation,
	@NotNull Submit submit,
	@NotEmpty String clubId,
	@Valid @NotNull List<ProcessRequestDTO.Save> processes
) {
	public Recruitment toRecruitment(Club club) {
		List<Process> processList = toProcesses();
		return Recruitment.builder()
			.title(title)
			.generation(generation)
			.club(club)
			.submit(submit)
			.position(position)
			.startAt(processList.get(0).getStartAt())
			.endAt(processList.get(0).getEndAt())
			.build();
	}

	public List<Process> toProcesses() {
		return processes.stream()
			.map(ProcessRequestDTO.Save::toProcess)
			.toList();
	}
}
