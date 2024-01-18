package com.yoyomo.domain.user.domain.service;

import com.yoyomo.domain.user.domain.entity.User;
import com.yoyomo.domain.user.domain.repository.UserRepository;
import com.yoyomo.domain.user.exception.UserConflictException;
import com.yoyomo.global.config.kakao.KakaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserSaveService {
    private final UserRepository userRepository;
    private final KakaoService kakaoService;
    public Void register(String token, String name, String number) throws Exception {
        String email = kakaoService.getEmail(token);
        if (userRepository.existsByEmail(email)){
            throw new UserConflictException();
        }
        User user = User.builder()
                .email(email)
                .name(name)
                .number(number)
                .build();
        userRepository.save(user);
        return null;
    }

    public Void testRegister(String email, String name, String number) {
        if (userRepository.existsByEmail(email)){
            throw new UserConflictException();
        }
        User user = User.builder()
                .email(email)
                .name(name)
                .number(number)
                .build();
        userRepository.save(user);
        return null;
    }
}
