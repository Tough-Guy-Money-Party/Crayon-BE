package com.yoyomo.domain.mail.application.usecase;

import com.yoyomo.domain.ApplicationTest;
import com.yoyomo.domain.mail.domain.entity.LimitInfo;
import com.yoyomo.global.common.redis.MailLimiter;
import com.yoyomo.global.common.redis.MailRedisTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

//@Disabled
@ComponentScan(includeFilters = @ComponentScan.Filter(
        type = FilterType.REGEX,
        pattern = "com.yoyomo.domain.mail.*")
)
class MailRateLimiterConcurrencyTest extends ApplicationTest {

    private static final UUID clubId1 = UUID.randomUUID();
    private static final UUID clubId2 = UUID.randomUUID();

    @Autowired
    MailLimiter mailLimiter;

    @Autowired
    RedisTemplate<String, String> rateLimitRedisTemplate;

    @Autowired
    MailRedisTemplate mailRedisTemplate;

    @Autowired
    RedissonClient redissonClient;

    @BeforeEach
    void resetKeys() {
        rateLimitRedisTemplate.delete("global:email:total");
        rateLimitRedisTemplate.delete("global:email:" + clubId1);
        rateLimitRedisTemplate.delete("global:email:" + clubId2);
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
                boolean consumed = mailLimiter.tryConsume(clubId1, perRequest);
                if (consumed) {
                    rejected.incrementAndGet();
                } else {
                    allowed.incrementAndGet();
                }
                latch.countDown();
            });
        }

        latch.await();
        executor.shutdown();

        assertThat(allowed.get()).isEqualTo(1);
        assertThat(rejected.get()).isEqualTo(1);
    }

    @DisplayName("동시 요청이 들어오면 후순위 요청은 선행 요청 처리 후 진행한다.")
    @Test
    void isRateLimited_withConcurrentOtherClubRequest() throws InterruptedException {
        // given
        int threadCount = 2;
        int perRequest = 200;
        mailRedisTemplate = new MailRedisTemplate(rateLimitRedisTemplate, new LimitInfo() {
            @Override
            public String getTotalKey() {
                return super.getTotalKey();
            }

            @Override
            public String getClubKey(UUID clubId) {
                return super.getClubKey(clubId);
            }

            @Override
            public long getMaxByKey(String key) {
                return 300;
            }
        });
        mailLimiter = new MailLimiter(redissonClient, mailRedisTemplate);

        // when
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);

        AtomicInteger allowed = new AtomicInteger(0);
        AtomicInteger rejected = new AtomicInteger(0);
        executor.submit(() -> {
            boolean consumed = mailLimiter.tryConsume(clubId1, perRequest);
            if (consumed) {
                allowed.incrementAndGet();
            } else {
                rejected.incrementAndGet();
            }
            latch.countDown();
        });

        executor.submit(() -> {
            boolean consumed = mailLimiter.tryConsume(clubId2, perRequest);
            if (consumed) {
                allowed.incrementAndGet();
            } else {
                rejected.incrementAndGet();
            }
            latch.countDown();
        });

        latch.await();
        executor.shutdown();

        // then
        assertThat(allowed.get()).isEqualTo(1);
        assertThat(rejected.get()).isEqualTo(1);
    }
}
