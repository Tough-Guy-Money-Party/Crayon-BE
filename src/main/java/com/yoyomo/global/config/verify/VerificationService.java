package com.yoyomo.global.config.verify;

import com.yoyomo.domain.application.application.dto.request.ApplicationVerificationRequestDto;
import com.yoyomo.global.config.verify.exception.InvalidMailCodeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class VerificationService {
    private final String PREFIX = "verification_code:";
    private final RedisTemplate<String, Object> redisTemplate;

    private static final SecureRandom secureRandom = new SecureRandom();

    public String GenerateCode(String email){
        String code = String.format("%06d", secureRandom.nextInt(900000) + 100000);
        log.info("generated code:{}",code);

        String key = PREFIX + email;
        log.info("key:{}",key);
        redisTemplate.opsForValue().set(key, code, 5, TimeUnit.MINUTES);

        return code;
    }

    public void verifyCode(ApplicationVerificationRequestDto.VerificationRequest dto){
        String key = PREFIX + dto.email();
        log.info("key:{}",key);
        String code  = (String) redisTemplate.opsForValue().get(key);
        log.info("code:{}",code);
        if(code != null && code.equals(dto.code())){
            redisTemplate.delete(key);
        } else {
            throw new InvalidMailCodeException();
        }
    }
}
