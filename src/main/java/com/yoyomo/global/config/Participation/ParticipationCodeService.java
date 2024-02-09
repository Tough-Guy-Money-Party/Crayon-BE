package com.yoyomo.global.config.Participation;

import com.yoyomo.global.config.Participation.dto.codeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class ParticipationCodeService {
    private final RedisTemplate<String, String> redisTemplate;
    public static final long EXPIRATION_TIME = 24 * 60 * 60 * 1000L;
    public String generate (String clubId) {
        String randomCode = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set(
                randomCode,
                clubId,
                EXPIRATION_TIME,
                TimeUnit.MILLISECONDS
        );

        redisTemplate.opsForValue().set(
                clubId,
                randomCode,
                EXPIRATION_TIME,
                TimeUnit.MILLISECONDS
        );

        redisTemplate.opsForValue().set(
                clubId+"_createdAt",
                LocalDateTime.now().toString(),
                EXPIRATION_TIME,
                TimeUnit.MILLISECONDS
        );

        return randomCode;
    }

    public codeResponse getCode (String clubId) {
        String randomCode = redisTemplate.opsForValue().get(clubId);
        if(randomCode == null){
            return new codeResponse(this.generate(clubId), LocalDateTime.now().toString() );
        }else{
            String createdAt = redisTemplate.opsForValue().get(clubId+"_createdAt");
            return new codeResponse(randomCode, createdAt);
        }
    }
}
