package com.yoyomo.domain.application.domain.vo;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class QuestionReplyTest {

	@DisplayName("데이터타입에 맞춰 포맷한다")
	@ParameterizedTest
	@CsvSource(value = {"datetime", "date", "timeofday"})
	void of(String type) {
		// given
		Question question = new Question("타임스탬프", type);
		Reply reply = new Reply("Date(2025,6,10,14,0,0)");

		// when
		QuestionReply questionReply = QuestionReply.of(question, reply);

		// then
		assertThat(questionReply.getReply().value()).isEqualTo("2025-06-10 14:00");
	}
}
