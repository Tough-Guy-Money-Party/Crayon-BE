package com.yoyomo.domain.mail.application.usecase;

import com.yoyomo.domain.ApplicationTest;
import com.yoyomo.global.common.MailRateLimiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.time.Duration;

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

    private static final String REDIS_KEY = "global:email";

    @BeforeEach
    void setup() {
        when(redisTemplate.opsForValue()).thenReturn(valueOps);
    }

    @DisplayName("발신 한도 제한을 넘지 않으면 참을 반환한다.")
    @Test
    void isRateLimited_whenRemainIsFull() {
        // given
        when(valueOps.get(REDIS_KEY)).thenReturn("50000");
        when(valueOps.decrement(REDIS_KEY)).thenReturn(49_999L);

        // when
        boolean isExceeded = mailRateLimiter.isRateLimited(1);

        // then
        assertThat(isExceeded).isFalse();
    }

    @DisplayName("발신 한도가 없으면 새롭게 한도를 설정한다.")
    @Test
    void isRateLimited_whenRemainIsNull() {
        // given
        when(valueOps.get(REDIS_KEY)).thenReturn(null);
        when(valueOps.decrement(REDIS_KEY)).thenReturn(49_999L);

        // when
        boolean isExceeded = mailRateLimiter.isRateLimited(1);

        // then
        verify(valueOps).set(eq(REDIS_KEY), eq("50000"), eq(Duration.ofHours(24)));
        assertThat(isExceeded).isFalse();
    }

    @DisplayName("발신 한도에 걸리면 거짓을 반환한다.")
    @Test
    void isRateLimited_whenRemainIsZero() {
        // given
        when(valueOps.get(REDIS_KEY)).thenReturn("0");

        // when
        boolean isExceeded = mailRateLimiter.isRateLimited(1);

        // then
        assertThat(isExceeded).isTrue();
    }

    @DisplayName("현재 발신 요청량이 남은 한도보다 크다면 거짓을 반환한다.")
    @Test
    void isRateLimited_whenRemainIsLessThanRequest() {
        // given
        when(valueOps.get(REDIS_KEY)).thenReturn("10");

        // when
        boolean isExceeded = mailRateLimiter.isRateLimited(11);

        // then
        assertThat(isExceeded).isTrue();
    }
}
