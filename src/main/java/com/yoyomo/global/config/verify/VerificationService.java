package com.yoyomo.global.config.verify;

import com.yoyomo.global.config.verify.exception.InvalidMailCodeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

import static com.yoyomo.domain.application.application.dto.request.ApplicationVerificationRequestDto.VerificationRequest;

@Slf4j
@Service
@RequiredArgsConstructor
public class VerificationService {
    private final String PREFIX = "verification_code:";
    private final RedisTemplate<String, String> redisTemplate;

    private static final SecureRandom secureRandom = new SecureRandom();

    public String GenerateCode(String email){
        String code = String.format("%06d", secureRandom.nextInt(900000) + 100000);

        String key = PREFIX + email;
        redisTemplate.opsForValue().set(key, code, 5, TimeUnit.MINUTES);

        return code;
    }

    public void verifyCode(VerificationRequest dto){
        String key = PREFIX + dto.email();
        String code  = redisTemplate.opsForValue().get(key);

        if(code != null && code.equals(dto.code())){
            redisTemplate.delete(key);
        } else {
            throw new InvalidMailCodeException();
        }
    }
}
