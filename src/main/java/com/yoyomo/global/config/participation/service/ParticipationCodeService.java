package com.yoyomo.global.config.participation.service;

import com.yoyomo.global.config.participation.dto.ParticipationCodeResponse;
import com.yoyomo.global.config.participation.exception.InvalidPaticipationCodeException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Primary
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

    public ParticipationCodeResponse getCode (String clubId, String isRegeneration) {
        String randomCode = redisTemplate.opsForValue().get(clubId);
        if(isRegeneration.equals("y") || randomCode == null){
            return new ParticipationCodeResponse(this.generate(clubId), LocalDateTime.now().toString() );
        }else{
            String createdAt = redisTemplate.opsForValue().get(clubId+"_createdAt");
            return new ParticipationCodeResponse(randomCode, createdAt);
        }
    }

    public String getClubId(String code) {
        String clubId = redisTemplate.opsForValue().get(code);
        if (clubId != null){
            return clubId;
        }
        throw new InvalidPaticipationCodeException();
    }
}
