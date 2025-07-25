package com.yoyomo.global.config.verify;

import static com.yoyomo.domain.application.application.dto.request.ApplicationVerificationRequest.*;

import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.yoyomo.global.config.verify.exception.InvalidMailCodeException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VerificationService {

	private static final int BOUND = 900000;
	private static final int OFFSET = 100000;
	private static final int EXPIRE_TIME = 5;
	private static final String CODE_LENGTH = "%06d";
	private static final String PREFIX = "verification_code:";
	private final SecureRandom secureRandom = new SecureRandom();

	private final RedisTemplate<String, String> redisTemplate;

	public String generateCode(String email) {
		String code = String.format(CODE_LENGTH, secureRandom.nextInt(BOUND) + OFFSET);

		String key = generate(email);
		redisTemplate.opsForValue().set(key, code, EXPIRE_TIME, TimeUnit.MINUTES);

		return code;
	}

	public void verifyCode(VerificationRequest dto) {
		String key = generate(dto.email());
		String code = redisTemplate.opsForValue().get(key);

		if (code == null || !code.equals(dto.code())) {
			throw new InvalidMailCodeException();
		}
		redisTemplate.delete(key);
	}

	private String generate(String email) {
		return PREFIX + email;
	}

}
