package com.yoyomo.domain.mail.application.usecase;

import com.yoyomo.domain.ApplicationTest;
import com.yoyomo.global.common.MailRateLimiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.time.Duration;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MailManageUseCaseImplTest extends ApplicationTest {

    @Mock
    private RedisTemplate<String, String> redisTemplate;

    @Mock
    private ValueOperations<String, String> valueOps;

    @InjectMocks
    private MailRateLimiter mailRateLimiter;

    private static final UUID clubId = UUID.randomUUID();
    private static final String TOTAL_KEY = "global:email:total";
    private static final String CLUB_KEY = "global:email:" + clubId;

    @BeforeEach
    void setup() {
        when(redisTemplate.opsForValue()).thenReturn(valueOps);
    }

    @DisplayName("발신 한도 제한을 넘지 않으면 참을 반환한다.")
    @Test
    void isRateLimited_whenRemainIsFull() {
        // given
        when(valueOps.get(TOTAL_KEY)).thenReturn("50000");
        when(valueOps.get(CLUB_KEY)).thenReturn("300");

        // when
        boolean isExceeded = mailRateLimiter.isRateLimited(clubId, 1);

        // then
        assertThat(isExceeded).isFalse();
    }

    @DisplayName("총 발신 한도가 없으면 새롭게 한도를 설정한다.")
    @Test
    void isRateLimited_whenTotalRemainIsNull() {
        // given
        when(valueOps.get(TOTAL_KEY)).thenReturn(null);
        when(valueOps.get(CLUB_KEY)).thenReturn("300");

        // when
        boolean isExceeded = mailRateLimiter.isRateLimited(clubId, 1);

        // then
        verify(valueOps).set(eq(TOTAL_KEY), eq("50000"), eq(Duration.ofHours(24)));
        assertThat(isExceeded).isFalse();
    }

    @DisplayName("동아리별 발신 한도가 없으면 새롭게 한도를 설정한다.")
    @Test
    void isRateLimited_whenClubRemainIsNull() {
        // given
        when(valueOps.get(TOTAL_KEY)).thenReturn("50000");
        when(valueOps.get(CLUB_KEY)).thenReturn(null);

        // when
        boolean isExceeded = mailRateLimiter.isRateLimited(clubId, 1);

        // then
        verify(valueOps).set(eq(CLUB_KEY), eq("300"), eq(Duration.ofHours(24)));
        assertThat(isExceeded).isFalse();
    }

    @DisplayName("발신 한도에 걸리면 거짓을 반환한다.")
    @ParameterizedTest
    @CsvSource({"0,300", "50000,0"})
    void isRateLimited_whenTotalRemainIsZero(String totalRemain, String clubRemain) {
        // given
        when(valueOps.get(TOTAL_KEY)).thenReturn(totalRemain);
        when(valueOps.get(CLUB_KEY)).thenReturn(clubRemain);

        // when
        boolean isExceeded = mailRateLimiter.isRateLimited(clubId, 1);

        // then
        assertThat(isExceeded).isTrue();
    }

    @DisplayName("현재 발신 요청량이 남은 한도보다 크다면 거짓을 반환한다.")
    @ParameterizedTest
    @CsvSource({"10,11", "11,10"})
    void isRateLimited_whenRemainIsLessThanRequest(String totalRemain, String clubRemain) {
        // given
        when(valueOps.get(TOTAL_KEY)).thenReturn(totalRemain);
        when(valueOps.get(CLUB_KEY)).thenReturn(clubRemain);

        // when
        boolean isExceeded = mailRateLimiter.isRateLimited(clubId, 11);

        // then
        assertThat(isExceeded).isTrue();
    }
}
