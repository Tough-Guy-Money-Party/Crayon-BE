package com.yoyomo.domain.mail.application.usecase;

import com.yoyomo.domain.ApplicationTest;
import com.yoyomo.global.common.MailRateLimiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

@Disabled
@ComponentScan(includeFilters = @ComponentScan.Filter(
        type = FilterType.REGEX,
        pattern = "com.yoyomo.domain.mail.*")
)
class MailRateLimiterConcurrencyTest extends ApplicationTest {

    @Autowired
    private MailRateLimiter mailRateLimiter;

    private final UUID clubId = UUID.randomUUID();

    @BeforeEach
    void resetKeys(@Autowired RedisTemplate<String, String> redisTemplate) {
        redisTemplate.delete("global:email:total");
        redisTemplate.delete("global:email:" + clubId);
    }

    @DisplayName("동시 요청의 합이 동아리 한도를 초과하면 선행 요청만 성공한다.")
    @Test
    void isRateLimited_withConcurrentRequest() throws InterruptedException {
        int threadCount = 2;
        int perRequest = 200;

        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);

        AtomicInteger allowed = new AtomicInteger(0);
        AtomicInteger rejected = new AtomicInteger(0);
        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                boolean limited = mailRateLimiter.isRateLimited(clubId, perRequest);
                if (limited) {
                    rejected.incrementAndGet();
                } else {
                    allowed.incrementAndGet();
                }
                latch.countDown();
            });
        }

        latch.await(10, TimeUnit.SECONDS);
        executor.shutdown();

        assertThat(allowed.get()).isEqualTo(1);
        assertThat(rejected.get()).isEqualTo(1);
    }
}
