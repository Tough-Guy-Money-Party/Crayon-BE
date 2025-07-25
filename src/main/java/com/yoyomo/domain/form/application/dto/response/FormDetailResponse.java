package com.yoyomo.domain.form.application.dto.response;

import java.util.List;

import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.form.domain.entity.Form;
import com.yoyomo.domain.item.application.dto.res.ItemResponse;
import com.yoyomo.domain.recruitment.domain.entity.Recruitment;

import lombok.Builder;

@Builder
public record FormDetailResponse(
	String clubName,
	String title,
	String position,
	List<ItemResponse> items
) {

	public static FormDetailResponse toResponse(Club clubName, Recruitment recruitment, Form form,
		List<ItemResponse> itemResponses) {
		return FormDetailResponse.builder()
			.clubName(clubName.getName())
			.title(form.getTitle())
			.position(recruitment.getPosition())
			.items(itemResponses)
			.build();
	}
}
