package com.yoyomo.domain.mail.application.usecase;

import com.yoyomo.domain.ApplicationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class MailManageUseCaseImplTest extends ApplicationTest {

    private static final UUID clubId = UUID.randomUUID();

    MockMailLimiter mailLimiter;

    @DisplayName("발신 한도 제한을 넘지 않으면 참을 반환한다.")
    @ParameterizedTest
    @CsvSource({"1,1,1", "300,300,300"})
    void tryConsume_whenRemainIsFull(int total, int club, int requestSize) {
        // given
        mailLimiter = new MockMailLimiter(total, club);

        // when
        boolean consumed = mailLimiter.tryConsume(clubId, requestSize);

        // then
        assertThat(consumed).isTrue();
    }

    @DisplayName("총 발신 한도가 없으면 새롭게 한도를 설정한다.")
    @Test
    void tryConsume_whenTotalRemainIsNull() {
        // given
        mailLimiter = new MockMailLimiter(null, 200);
        int requestSize = 1;

        // when
        boolean consumed = mailLimiter.tryConsume(clubId, requestSize);

        // then
        assertThat(consumed).isTrue();
        assertThat(mailLimiter.totalLimit).isEqualTo(MockMailLimiter.INIT_TOTAL_LIMIT - requestSize);
    }

    @DisplayName("동아리 발신 한도가 없으면 새롭게 한도를 설정한다.")
    @Test
    void tryConsume_whenClubRemainIsNull() {
        // given
        mailLimiter = new MockMailLimiter(200, null);
        int requestSize = 1;

        // when
        boolean consumed = mailLimiter.tryConsume(clubId, requestSize);

        // then
        assertThat(consumed).isTrue();
        assertThat(mailLimiter.clubLimit).isEqualTo(MockMailLimiter.INIT_CLUB_LIMIT - requestSize);
    }

    @DisplayName("발신 한도를 초과하면 거짓을 반환한다.")
    @ParameterizedTest
    @CsvSource({"0,300", "50000,0"})
    void tryConsume_whenTotalRemainIsZero(int totalRemain, int clubRemain) {
        // given
        mailLimiter = new MockMailLimiter(totalRemain, clubRemain);
        int requestSize = 1;

        // when
        boolean consumed = mailLimiter.tryConsume(clubId, requestSize);

        // then
        assertThat(consumed).isFalse();
    }

    @DisplayName("현재 발신 요청량이 남은 한도보다 크다면 거짓을 반환한다.")
    @ParameterizedTest
    @CsvSource({"10,11", "11,10"})
    void tryConsume_whenRemainIsLessThanRequest(int totalRemain, int clubRemain) {
        // given
        mailLimiter = new MockMailLimiter(totalRemain, clubRemain);
        int requestSize = 11;

        // when
        boolean consumed = mailLimiter.tryConsume(clubId, requestSize);

        // then
        assertThat(consumed).isFalse();
    }

    @DisplayName("현재 발신 요청량이 초기화 한도보다 크다면 거짓을 반환한다.")
    @ParameterizedTest
    @CsvSource({"301", "50001"})
    void tryConsume_whenLimitIsLessThanRequest(int requestSize) {
        // given
        mailLimiter = new MockMailLimiter(null, null);

        // when
        boolean consumed = mailLimiter.tryConsume(clubId, requestSize);

        // then
        assertThat(consumed).isFalse();
    }
}
