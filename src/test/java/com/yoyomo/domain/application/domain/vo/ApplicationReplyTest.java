package com.yoyomo.domain.application.domain.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ApplicationReplyTest {

    @DisplayName("지원자 정보(이름, 전화번호, 이메일)와 일반 질문 답변으로 분리한다.")
    @Test
    void of() {
        // given
        List<QuestionReply> questionReplies = List.of(
                new QuestionReply(new Question("이름이 뭐에요", "string"), new Reply("나아연")),
                new QuestionReply(new Question("전화번호 뭐에요", "string"), new Reply("01099998888")),
                new QuestionReply(new Question("꿈이 뭐에요", "string"), new Reply("백만장자")),
                new QuestionReply(new Question("생일?", "datetime"), new Reply("Date(2001,3,22)"))
        );

        // when
        ApplicationReply applicationReply = ApplicationReply.of(questionReplies);

        // then
        Applicant applicant = applicationReply.getApplicant();
        assertThat(applicant).isNotNull();
        assertAll(
                () -> assertThat(applicant.getName()).isEqualTo("나아연"),
                () -> assertThat(applicant.getPhone()).isEqualTo("01099998888"),
                () -> assertThat(applicant.getEmail()).isEmpty()
        );

        assertThat(applicationReply.getFormQuestionReplies()).hasSize(2);
    }
}
