package com.yoyomo.domain.mail.application.usecase;

import com.yoyomo.global.common.redis.MailLimiter;

import java.util.UUID;

public class MockMailLimiter implements MailLimiter {

    static final int INIT_TOTAL_LIMIT = 50_000;
    static final int INIT_CLUB_LIMIT = 299;

    Integer totalLimit;
    Integer clubLimit;

    public MockMailLimiter(Integer totalLimit, Integer clubLimit) {
        this.totalLimit = totalLimit;
        this.clubLimit = clubLimit;
    }

    @Override
    public boolean tryConsume(UUID clubId, int requestSize) {
        if (this.totalLimit == null) {
            this.totalLimit = INIT_TOTAL_LIMIT;
        }
        if (this.clubLimit == null) {
            this.clubLimit = INIT_CLUB_LIMIT;
        }

        if (this.totalLimit < requestSize || this.clubLimit < requestSize) {
            return false;
        }

        totalLimit -= requestSize;
        clubLimit -= requestSize;
        return true;
    }
}
