package com.yoyomo.domain.application.application.dto.request;

import com.yoyomo.domain.application.domain.vo.ApplicationReply;
import com.yoyomo.domain.application.domain.vo.QuestionReply;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ApplicationImportRequestTest {

    @DisplayName("응답에 null이 들어오는 경우 빈 응답으로 처리한다")
    @Test
    void toApplicationReplies() {
        // given
        List<QuestionRequest> cols = List.of(
                new QuestionRequest("타임스탬프", "datetime"),
                new QuestionRequest("질문", "string")
        );


        List<RespondentRequest> rows = List.of(
                new RespondentRequest(Arrays.asList(
                        new DataRequest("Date(2025,6,10,14,0,0)"),
                        null
                ))
        );

        // when
        List<ApplicationReply> applicationReplies = new ApplicationImportRequest(cols, rows).toApplicationReplies();

        // then
        assertThat(applicationReplies).hasSize(1);

        List<QuestionReply> formQuestionReplies = applicationReplies.get(0).getFormQuestionReplies();
        assertThat(formQuestionReplies).hasSize(2);
        assertThat(formQuestionReplies.get(0).getReply().isEmpty()).isFalse();
        assertThat(formQuestionReplies.get(1).getReply().isEmpty()).isTrue();
    }
}
