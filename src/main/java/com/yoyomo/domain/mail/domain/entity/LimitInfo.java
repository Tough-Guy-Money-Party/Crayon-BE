package com.yoyomo.domain.mail.domain.entity;

import com.yoyomo.domain.mail.exception.InvalidLimitKeyException;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.Duration;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.UUID;

import static com.yoyomo.domain.mail.presentation.constant.ResponseMessage.INVALID_LIMIT_KEY;

@Component
public class LimitInfo {

    private static final int DAYS_TTL_RESET = 1;

    private static final String TOTAL_KEY = "global:email:total";
    private static final long TOTAL_MAX = 50_000;

    private static final String CLUB_KEY_PREFIX = "global:email:";
    private static final long CLUB_MAX = 300;

    private final Clock clock;

    public LimitInfo() {
        this.clock = Clock.systemUTC();
    }

    public LimitInfo(Clock clock) {
        this.clock = clock;
    }

    public String getTotalKey() {
        return TOTAL_KEY;
    }

    public String getClubKey(UUID clubId) {
        return CLUB_KEY_PREFIX + clubId;
    }

    public long getMaxByKey(String key) {
        if (TOTAL_KEY.equals(key)) {
            return TOTAL_MAX;
        }
        if (key.startsWith(CLUB_KEY_PREFIX)) {
            return CLUB_MAX;
        }
        throw new InvalidLimitKeyException(INVALID_LIMIT_KEY.getMessage());
    }

    public Duration getTTL() {
        ZonedDateTime now = ZonedDateTime.now(clock.withZone(ZoneOffset.UTC));
        ZonedDateTime nextMidnight = now.toLocalDate()
                .plusDays(DAYS_TTL_RESET)
                .atStartOfDay(ZoneOffset.UTC);
        return Duration.between(now, nextMidnight);
    }
}
