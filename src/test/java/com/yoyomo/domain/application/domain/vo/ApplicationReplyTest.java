package com.yoyomo.domain.application.domain.vo;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ApplicationReplyTest {

	@DisplayName("지원자 정보(이름, 전화번호, 이메일)와 일반 질문 답변으로 분리한다.")
	@Test
	void of() {
		// given
		List<QuestionReply> questionReplies = List.of(
			QuestionReply.of(new Question("이름이 뭐에요", "string"), new Reply("나아연")),
			QuestionReply.of(new Question("전화번호 뭐에요", "string"), new Reply("01099998888")),
			QuestionReply.of(new Question("꿈이 뭐에요", "string"), new Reply("백만장자")),
			QuestionReply.of(new Question("생일?", "datetime"), new Reply("Date(2001,3,22)")),
			QuestionReply.of(new Question("타임스탬프", "datetime"), new Reply("Date(2025,7,4,15,40,0)"))
		);

		// when
		ApplicationReply applicationReply = ApplicationReply.of(questionReplies);

		// then
		Applicant applicant = applicationReply.getApplicant();
		assertThat(applicant).isNotNull();
		assertAll(
			() -> assertThat(applicant.getName()).isEqualTo("나아연"),
			() -> assertThat(applicant.getPhone()).isEqualTo("01099998888"),
			() -> assertThat(applicant.getEmail()).isEmpty(),
			() -> assertThat(applicant.getAppliedAt()).isPresent(),
			() -> assertThat(applicant.getAppliedAt().get()).isEqualTo(LocalDateTime.of(2025, 7, 4, 15, 40, 0))
		);

		assertThat(applicationReply.getFormQuestionReplies()).hasSize(2);
	}
}
