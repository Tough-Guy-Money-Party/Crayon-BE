package com.yoyomo.domain.mail.domain.entity;

import com.yoyomo.domain.ApplicationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.assertj.core.api.Assertions.assertThat;

class LimitInfoTest extends ApplicationTest {

    @DisplayName("만료 시각은 다음 날 자정이다.")
    @Test
    void getExpireAt_shouldReturnMidnightEpochForTomorrowUTC() {
        // given
        LimitInfo limitInfo = new LimitInfo();

        // when
        long expireAt = limitInfo.getExpireAt();

        // then
        LocalDateTime expectedDateTime = LocalDate.now(ZoneOffset.UTC)
                .plusDays(1)
                .atStartOfDay();

        long expectedEpoch = expectedDateTime.toEpochSecond(ZoneOffset.UTC);

        assertThat(expireAt).isEqualTo(expectedEpoch);
    }
}
