package com.yoyomo.domain.recruitment.domain.dto;

import java.util.List;
import java.util.UUID;

import com.yoyomo.domain.recruitment.domain.entity.Recruitment;

public record RecruitmentCreateResponse(
	List<UUID> recruitmentId
) {
	public static RecruitmentCreateResponse from(List<Recruitment> recruitment) {
		return new RecruitmentCreateResponse(
			recruitment.stream()
				.map(Recruitment::getId)
				.toList()
		);
	}
}
