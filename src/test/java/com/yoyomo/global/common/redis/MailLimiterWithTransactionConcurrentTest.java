package com.yoyomo.global.common.redis;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig(TestConfig.class)
@TestPropertySource(locations = "classpath:application.yml")
class MailLimiterWithTransactionConcurrentTest {

    private static final UUID clubId1 = UUID.randomUUID();
    private static final String TOTAL_MAIL_KEY = "mail:total";
    private static final String CLUB1_MAIL_KEY = "mail:" + clubId1;

    @Autowired
    MailLimiterWithLock mailLimiter;

    @Autowired
    @Qualifier("rateLimitRedisTemplate")
    RedisTemplate<String, Long> rateLimitRedisTemplate;

    @DisplayName("동시 요청의 합이 동아리 한도를 초과하면 선행 요청만 성공한다.")
    @Test
    void isRateLimited_withConcurrentRequest() throws InterruptedException {
        rateLimitRedisTemplate.opsForValue().set(TOTAL_MAIL_KEY, 0L);
        rateLimitRedisTemplate.opsForValue().set(CLUB1_MAIL_KEY, 0L);

        int threadCount = 2;
        int perRequest = 200;

        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);

        AtomicInteger allowed = new AtomicInteger(0);
        AtomicInteger rejected = new AtomicInteger(0);
        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                boolean consumed = mailLimiter.tryConsume(clubId1, perRequest);
                if (consumed) {
                    allowed.incrementAndGet();
                } else {
                    rejected.incrementAndGet();
                }
                latch.countDown();
            });
        }

        latch.await();
        executor.shutdown();

        assertThat(allowed.get()).isEqualTo(1);
        assertThat(rejected.get()).isEqualTo(1);
        assertThat(rateLimitRedisTemplate.opsForValue().get(TOTAL_MAIL_KEY)).isEqualTo(200);
        assertThat(rateLimitRedisTemplate.opsForValue().get(CLUB1_MAIL_KEY)).isEqualTo(200);
    }

    @DisplayName("동시 요청이 들어오면 후순위 요청은 선행 요청 처리 후 진행한다.")
    @Test
    void isRateLimited_withConcurrentOtherClubRequest() throws InterruptedException {
        // given
        int threadCount = 10;
        int perRequest = 200;

        rateLimitRedisTemplate.opsForValue().set(TOTAL_MAIL_KEY, 50_000L - (threadCount / 2) * perRequest);

        UUID[] clubIds = new UUID[threadCount];
        for (int i = 0; i < threadCount; i++) {
            clubIds[i] = UUID.randomUUID();
        }

        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);

        AtomicInteger allowed = new AtomicInteger(0);
        AtomicInteger rejected = new AtomicInteger(0);

        // when
        for (int i = 0; i < threadCount; i++) {
            final UUID clubId = clubIds[i];
            executor.submit(() -> {
                boolean consumed = mailLimiter.tryConsume(clubId, perRequest);
                if (consumed) {
                    allowed.incrementAndGet();
                } else {
                    rejected.incrementAndGet();
                }
                latch.countDown();
            });
        }

        latch.await();
        executor.shutdown();

        // then
        assertThat(allowed.get()).isEqualTo(threadCount / 2);
        assertThat(rejected.get()).isEqualTo(threadCount / 2);
        assertThat(rateLimitRedisTemplate.opsForValue().get(TOTAL_MAIL_KEY)).isEqualTo(50_000L);
    }
}
