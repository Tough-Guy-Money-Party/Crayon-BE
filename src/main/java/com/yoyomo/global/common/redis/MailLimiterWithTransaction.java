package com.yoyomo.global.common.redis;

import java.util.List;
import java.util.UUID;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Component;

import com.yoyomo.domain.mail.domain.entity.LimitInfo;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MailLimiterWithTransaction implements MailLimiter {

	private static final int TOTAL_CONSUME_RESULT_INDEX = 0;
	private static final int CLUB_CONSUME_RESULT_INDEX = 1;

	private final LimitInfo limitInfo;
	private final RedisTemplate<String, Long> rateLimitRedisTemplate;

	/**
	 * 한도: 누적 사용량 관리
	 *
	 * @param clubId
	 * @param requestSize
	 * @return
	 */

	@Override
	public boolean tryConsume(UUID clubId, int requestSize) {
		String totalKey = limitInfo.getTotalKey();
		String clubKey = limitInfo.getClubKey(clubId);

		int tryCount = 1;
		return consume(requestSize, totalKey, clubKey, tryCount);
	}

	private boolean consume(int requestSize, String totalKey, String clubKey, int tryCount) {
		if (tryCount > 3) {
			return false;
		}

		List<Long> results = rateLimitRedisTemplate.execute(new SessionCallback<>() {

			@Override
			public List<Long> execute(RedisOperations operations) throws DataAccessException {
				try {
					operations.watch(List.of(totalKey, clubKey));

					operations.multi();
					operations.opsForValue().increment(totalKey, requestSize);
					operations.opsForValue().increment(clubKey, requestSize);

					return operations.exec();
				} catch (RuntimeException e) {
					operations.discard();
					throw e;
				}
			}
		});

		if (results == null || results.isEmpty()) {
			return consume(requestSize, totalKey, clubKey, tryCount + 1);
		}

		if (isOver(results, totalKey, clubKey)) {
			rollback(totalKey, clubKey, requestSize);
			return false;
		}

		return true;
	}

	private boolean isOver(List<Long> results, String totalKey, String clubKey) {
		long totalCount = results.get(TOTAL_CONSUME_RESULT_INDEX);
		long clubCount = results.get(CLUB_CONSUME_RESULT_INDEX);
		return (totalCount > limitInfo.getMaxByKey(totalKey)) || (clubCount > limitInfo.getMaxByKey(clubKey));
	}

	private void rollback(String totalKey, String clubKey, long requestSize) {
		rateLimitRedisTemplate.execute(new SessionCallback<>() {

			@Override
			public List<Long> execute(RedisOperations operations) throws DataAccessException {
				try {
					operations.watch(List.of(totalKey, clubKey));

					operations.multi();
					operations.opsForValue().decrement(totalKey, requestSize);
					operations.opsForValue().decrement(clubKey, requestSize);

					return operations.exec();
				} catch (RuntimeException e) {
					operations.discard();
					throw e;
				}
			}
		});
	}

}
