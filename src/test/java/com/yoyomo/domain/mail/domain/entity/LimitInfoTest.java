package com.yoyomo.domain.mail.domain.entity;

import com.yoyomo.domain.ApplicationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;

import static org.assertj.core.api.Assertions.assertThat;

class LimitInfoTest extends ApplicationTest {

    @DisplayName("다음 날 자정까지 남은 시간을 반환한다.")
    @Test
    void getTTL() {
        // given
        Clock fixedClock = Clock.fixed(
                Instant.parse("2025-04-12T21:15:30Z"),
                ZoneOffset.UTC
        );
        LimitInfo limitInfo = new LimitInfo(fixedClock);

        // when
        Duration ttl = limitInfo.getTTL();

        // then
        assertThat(ttl.getSeconds()).isEqualTo(Duration.ofHours(2).plusMinutes(44).plusSeconds(30).getSeconds());
    }
}
