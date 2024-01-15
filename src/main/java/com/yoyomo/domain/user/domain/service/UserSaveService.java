package com.yoyomo.domain.user.domain.service;

import com.yoyomo.domain.user.domain.entity.User;
import com.yoyomo.domain.user.domain.repository.UserRepository;
import com.yoyomo.global.config.jwt.JwtProvider;
import com.yoyomo.global.config.kakao.KakaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserSaveService {
    private final UserRepository userRepository;
    private final KakaoService kakaoService;
    public ResponseEntity<Void> register(String token, String name, String number) throws Exception {
        String email = kakaoService.getEmail(token);
        if (userRepository.existsByEmail(email))
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        User user = User.builder()
                .email(email)
                .name(name)
                .number(number)
                .build();
        userRepository.save(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<Void> testRegister(String email, String name, String number) throws Exception {
        if (userRepository.existsByEmail(email))
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        User user = User.builder()
                .email(email)
                .name(name)
                .number(number)
                .build();
        userRepository.save(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
