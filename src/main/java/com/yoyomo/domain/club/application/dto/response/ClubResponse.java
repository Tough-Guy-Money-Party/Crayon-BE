package com.yoyomo.domain.club.application.dto.response;

public class ClubResponse {

	public record Response(
		String id,
		String name
	) {
	}

	public record Participation(
		String id,
		String name
	) {
	}

	public record Code(
		String code
	) {
	}
}
