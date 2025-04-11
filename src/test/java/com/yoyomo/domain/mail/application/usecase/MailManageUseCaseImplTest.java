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

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MailManageUseCaseImplTest extends ApplicationTest {

    private static final UUID clubId = UUID.randomUUID();
    private static final String TOTAL_KEY = "global:email:total";
    private static final String CLUB_KEY = "global:email:" + clubId;
    private static final long TOTAL_LIMIT = 50_000L;
    private static final long CLUB_LIMIT = 300L;

    @Mock
    private RedisTemplate<String, String> redisTemplate;

    @Mock
    private ValueOperations<String, String> valueOps;

    @InjectMocks
    private MailRateLimiter mailRateLimiter;

    @BeforeEach
    void setup() {
        when(redisTemplate.opsForValue()).thenReturn(valueOps);
    }

    @DisplayName("발신 한도 제한을 넘지 않으면 참을 반환한다.")
    @ParameterizedTest
    @CsvSource({"1", "300"})
    void isRateLimited_whenRemainIsFull(int requestSize) {
        // given
        when(valueOps.setIfAbsent(any(), any(), any())).thenReturn(Boolean.FALSE);
        when(valueOps.decrement(TOTAL_KEY, requestSize)).thenReturn(TOTAL_LIMIT - requestSize);
        when(valueOps.decrement(CLUB_KEY, requestSize)).thenReturn(CLUB_LIMIT - requestSize);

        // when
        boolean isExceeded = mailRateLimiter.isRateLimited(clubId, requestSize);

        // then
        assertThat(isExceeded).isFalse();
    }

    @DisplayName("총 발신 한도가 없으면 새롭게 한도를 설정한다.")
    @Test
    void isRateLimited_whenTotalRemainIsNull() {
        // given
        int requestSize = 1;
        when(valueOps.setIfAbsent(eq(TOTAL_KEY), any(), any())).thenReturn(Boolean.TRUE);
        when(valueOps.setIfAbsent(eq(CLUB_KEY), any(), any())).thenReturn(Boolean.FALSE);

        // when
        mailRateLimiter.isRateLimited(clubId, requestSize);

        // then
        verify(valueOps, never()).decrement(TOTAL_KEY, requestSize);
        verify(valueOps).decrement(CLUB_KEY, requestSize);
    }

    @DisplayName("동아리 발신 한도가 없으면 새롭게 한도를 설정한다.")
    @Test
    void isRateLimited_whenClubRemainIsNull() {
        // given
        int requestSize = 1;
        when(valueOps.setIfAbsent(eq(TOTAL_KEY), any(), any())).thenReturn(Boolean.FALSE);
        when(valueOps.setIfAbsent(eq(CLUB_KEY), any(), any())).thenReturn(Boolean.TRUE);

        // when
        mailRateLimiter.isRateLimited(clubId, requestSize);

        // then
        verify(valueOps).decrement(TOTAL_KEY, requestSize);
        verify(valueOps, never()).decrement(CLUB_KEY, requestSize);
    }

    @DisplayName("발신 한도를 초과하면 거짓을 반환한다.")
    @ParameterizedTest
    @CsvSource({"0,300", "50000,0"})
    void isRateLimited_whenTotalRemainIsZero(long totalRemain, long clubRemain) {
        // given
        int requestSize = 1;
        when(valueOps.setIfAbsent(eq(TOTAL_KEY), any(), any())).thenReturn(Boolean.FALSE);
        when(valueOps.setIfAbsent(eq(CLUB_KEY), any(), any())).thenReturn(Boolean.FALSE);

        when(valueOps.decrement(TOTAL_KEY, requestSize)).thenReturn(totalRemain - requestSize);
        when(valueOps.decrement(CLUB_KEY, requestSize)).thenReturn(clubRemain - requestSize);

        // when
        boolean isExceeded = mailRateLimiter.isRateLimited(clubId, requestSize);

        // then
        assertThat(isExceeded).isTrue();
    }

    @DisplayName("현재 발신 요청량이 남은 한도보다 크다면 거짓을 반환한다.")
    @ParameterizedTest
    @CsvSource({"10,11", "11,10"})
    void isRateLimited_whenRemainIsLessThanRequest(long totalRemain, long clubRemain) {
        // given
        int requestSize = 11;
        when(valueOps.setIfAbsent(eq(TOTAL_KEY), any(), any())).thenReturn(Boolean.FALSE);
        when(valueOps.setIfAbsent(eq(CLUB_KEY), any(), any())).thenReturn(Boolean.FALSE);

        when(valueOps.decrement(TOTAL_KEY, requestSize)).thenReturn(totalRemain - requestSize);
        when(valueOps.decrement(CLUB_KEY, requestSize)).thenReturn(clubRemain - requestSize);

        // when
        boolean isExceeded = mailRateLimiter.isRateLimited(clubId, requestSize);

        // then
        assertThat(isExceeded).isTrue();
    }


    @DisplayName("현재 발신 요청량이 초기화 한도보다 크다면 거짓을 반환한다.")
    @ParameterizedTest
    @CsvSource({"301", "50001"})
    void isRateLimited_whenLimitIsLessThanRequest(int requestSize) {
        // when
        boolean isExceeded = mailRateLimiter.isRateLimited(clubId, requestSize);

        // then
        assertThat(isExceeded).isTrue();
    }
}
