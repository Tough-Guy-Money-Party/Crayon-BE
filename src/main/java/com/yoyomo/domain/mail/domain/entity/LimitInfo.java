package com.yoyomo.domain.mail.domain.entity;

import static com.yoyomo.domain.mail.presentation.constant.ResponseMessage.*;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.yoyomo.domain.mail.exception.InvalidLimitKeyException;

@Component
public class LimitInfo {

	private static final int DAYS_TTL_RESET = 1;
	private static final String MAIL_KEY_PREFIX = "mail:";
	private static final String TOTAL_MAIL_KEY = MAIL_KEY_PREFIX + "total";
	private static final long CLUB_MAX = 300;
	private static final long TOTAL_MAX = 50_000;

	public String getTotalKey() {
		return TOTAL_MAIL_KEY;
	}

	public String getClubKey(UUID clubId) {
		return MAIL_KEY_PREFIX + clubId;
	}

	public long getMaxByKey(String key) {
		if (TOTAL_MAIL_KEY.equals(key)) {
			return TOTAL_MAX;
		}
		if (key.startsWith(MAIL_KEY_PREFIX)) {
			return CLUB_MAX;
		}
		throw new InvalidLimitKeyException(INVALID_LIMIT_KEY.getMessage());
	}

	public long getExpireAt() {
		return LocalDate.now(ZoneOffset.UTC)
			.plusDays(DAYS_TTL_RESET)
			.atStartOfDay()
			.toEpochSecond(ZoneOffset.UTC);
	}
}
