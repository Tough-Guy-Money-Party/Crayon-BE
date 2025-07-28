package com.yoyomo.domain.application.domain.vo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.yoyomo.global.common.util.DateFormatter;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
@AllArgsConstructor
public class Applicant {

	private final Map<ApplicantInfo, Reply> values;

	public static Applicant from(List<QuestionReply> questionReplies) {
		Map<ApplicantInfo, Reply> applicantInfos = questionReplies.stream()
			.collect(Collectors.toMap(ApplicantInfo::find, QuestionReply::getReply));
		return new Applicant(applicantInfos);
	}

	public String getName() {
		return values.getOrDefault(ApplicantInfo.NAME, Reply.empty()).value();
	}

	public String getPhone() {
		return values.getOrDefault(ApplicantInfo.PHONE, Reply.empty()).value();
	}

	public String getEmail() {
		return values.getOrDefault(ApplicantInfo.EMAIL, Reply.empty()).value();
	}

	public LocalDateTime getAppliedAt() {
		Reply reply = values.getOrDefault(ApplicantInfo.APPLIED_AT, Reply.empty());
		if (reply.isEmpty()) {
			return LocalDateTime.now();
		}
		return DateFormatter.parse(reply.value());
	}
}
