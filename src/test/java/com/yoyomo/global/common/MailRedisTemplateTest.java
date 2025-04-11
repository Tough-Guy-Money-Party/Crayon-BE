package com.yoyomo.global.common;

import com.yoyomo.domain.ApplicationTest;
import com.yoyomo.domain.mail.domain.entity.LimitInfo;
import com.yoyomo.global.common.redis.MailRedisTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class MailRedisTemplateTest extends ApplicationTest {

    private static final String TOTAL_KEY = "global:email:total";
    private static final String CLUB_KEY = "global:email:clubId";
    private static final long TOTAL_LIMIT = 50_000L;
    private static final long CLUB_LIMIT = 300L;

    @Mock
    RedisTemplate<String, String> redisTemplate;

    @Mock
    ValueOperations<String, String> valueOps;

    @Mock
    LimitInfo limitInfo;

    @InjectMocks
    MailRedisTemplate mailRedisTemplate;

    @BeforeEach
    void setup() {
        when(redisTemplate.opsForValue()).thenReturn(valueOps);
    }

    @DisplayName("키가 없으면 최대 할당량을 반환한다.")
    @ParameterizedTest
    @CsvSource(value = {TOTAL_KEY + "," + TOTAL_LIMIT, CLUB_KEY + "," + CLUB_LIMIT})
    void tryConsume_whenRemainIsFull(String key, long limit) {
        // given
        when(valueOps.get(key)).thenReturn(null);
        when(limitInfo.getMaxByKey(TOTAL_KEY)).thenReturn(TOTAL_LIMIT);
        when(limitInfo.getMaxByKey(CLUB_KEY)).thenReturn(CLUB_LIMIT);

        // when
        long quota = mailRedisTemplate.getQuota(key);

        // then
        assertThat(quota).isEqualTo(limit);
    }
}
