package com.yoyomo.domain.application.domain.vo;

import java.util.Arrays;

import com.yoyomo.domain.application.exception.InvalidApplicantInfoException;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ApplicantInfo {
	NAME("이름"),
	PHONE("전화번호"),
	EMAIL("메일"),
	APPLIED_AT("타임스탬프");

	private final String keyword;

	public static ApplicantInfo find(QuestionReply questionReply) {
		return Arrays.stream(values())
			.filter(info -> questionReply.match(info.keyword))
			.findFirst()
			.orElseThrow(InvalidApplicantInfoException::new);
	}

	public static boolean anyMatch(String title) {
		return Arrays.stream(values())
			.anyMatch(info -> title.contains(info.keyword));
	}
}
