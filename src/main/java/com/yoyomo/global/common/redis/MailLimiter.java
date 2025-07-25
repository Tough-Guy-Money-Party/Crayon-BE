package com.yoyomo.global.common.redis;

import java.util.UUID;

public interface MailLimiter {

	boolean tryConsume(UUID clubId, int requestSize);
}
