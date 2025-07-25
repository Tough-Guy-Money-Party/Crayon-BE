package com.yoyomo.domain.application.application.dto.response;

import java.util.List;

import com.yoyomo.domain.club.application.dto.response.ClubResponse;
import com.yoyomo.domain.recruitment.application.dto.response.ProcessInfoResponse;

public class ApplicationResponse {

	public record Response(
		String id,
		ClubResponse.Response club,
		List<ProcessInfoResponse.Info> processes,
		Integer currentStage
	) {
	}
}
