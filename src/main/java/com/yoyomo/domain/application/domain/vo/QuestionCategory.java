package com.yoyomo.domain.application.domain.vo;

public enum QuestionCategory {
	APPLICANT_INFO,
	FORM;

	public static QuestionCategory match(Question question) {
		if (question.isApplicantInfo()) {
			return APPLICANT_INFO;
		}
		return FORM;
	}
}
