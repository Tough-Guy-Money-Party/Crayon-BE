package com.yoyomo.domain.application.domain.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ReplyTest {

    @DisplayName("날짜와 시간을 포맷팅한다.")
    @Test
    void format_datetime() {
        // given
        Reply dateReply = new Reply("Date(2025,6,4,15,0,0)");

        // when
        Reply reply = dateReply.format(DataType.DATE_TIME);

        // then
        assertThat(reply.value()).isEqualTo("2025-06-04 15:00");
    }

    @DisplayName("날짜를 포맷팅한다.")
    @Test
    void formatDate() {
        // given
        Reply dateReply = new Reply("Date(2025,6,4)");

        // when
        Reply reply = dateReply.format(DataType.DATE);

        // then
        assertThat(reply.value()).isEqualTo("2025-06-04");
    }
}
