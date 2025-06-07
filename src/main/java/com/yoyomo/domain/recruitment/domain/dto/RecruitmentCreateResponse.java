package com.yoyomo.domain.recruitment.domain.dto;

import java.util.UUID;

import com.yoyomo.domain.recruitment.domain.entity.Recruitment;

public record RecruitmentCreateResponse(
	UUID recruitmentId
) {
	public static RecruitmentCreateResponse from(Recruitment recruitment) {
		return new RecruitmentCreateResponse(
			recruitment.getId()
		);
	}
}
