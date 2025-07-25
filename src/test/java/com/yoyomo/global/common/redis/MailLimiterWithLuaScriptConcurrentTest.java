package com.yoyomo.global.common.redis;

import static org.assertj.core.api.Assertions.*;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(TestConfig.class)
class MailLimiterWithLuaScriptConcurrentTest {

	private static final UUID clubId1 = UUID.randomUUID();
	private static final String TOTAL_MAIL_KEY = "mail:total";
	private static final String CLUB1_MAIL_KEY = "mail:" + clubId1;

	@Autowired
	MailLimiterWithLuaScript mailLimiter;

	@Autowired
	RedisTemplate<String, Long> rateLimitRedisTemplate;

	@DisplayName("동시 요청의 합이 동아리 한도를 초과하면 선행 요청만 성공한다.")
	@Test
	void isRateLimited_withConcurrentRequest() throws InterruptedException {
		// given
		rateLimitRedisTemplate.opsForValue().set(TOTAL_MAIL_KEY, 50_000L);
		rateLimitRedisTemplate.opsForValue().set(CLUB1_MAIL_KEY, 300L);

		int threadCount = 2;
		int perRequest = 200;

		ExecutorService executor = Executors.newFixedThreadPool(threadCount);
		CountDownLatch latch = new CountDownLatch(threadCount);

		AtomicInteger allowed = new AtomicInteger(0);
		AtomicInteger rejected = new AtomicInteger(0);

		// when
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

		// then
		assertThat(allowed.get()).isEqualTo(1);
		assertThat(rejected.get()).isEqualTo(1);
		assertThat(rateLimitRedisTemplate.opsForValue().get(TOTAL_MAIL_KEY)).isEqualTo(49_800);
		assertThat(rateLimitRedisTemplate.opsForValue().get(CLUB1_MAIL_KEY)).isEqualTo(100);
	}

	@DisplayName("동시 요청이 들어오면 후순위 요청은 선행 요청 처리 후 진행한다.")
	@Test
	void isRateLimited_withConcurrentOtherClubRequest() throws InterruptedException {
		// given
		int threadCount = 2;
		int perRequest = 200;

		rateLimitRedisTemplate.opsForValue().set(TOTAL_MAIL_KEY, (long)(threadCount / 2) * perRequest);

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
		assertThat(rejected.get()).isEqualTo(threadCount / 2);
		assertThat(allowed.get()).isEqualTo(threadCount / 2);
		assertThat(rateLimitRedisTemplate.opsForValue().get(TOTAL_MAIL_KEY)).isEqualTo(0L);
	}
}
