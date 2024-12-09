package com.yoyomo.domain.user.application.usecase;

import com.yoyomo.domain.user.application.dto.response.UserResponseDTO;
import com.yoyomo.domain.user.application.mapper.UserMapper;
import com.yoyomo.domain.user.domain.entity.User;
import com.yoyomo.domain.user.domain.service.UserGetService;
import com.yoyomo.domain.user.domain.service.UserSaveService;
import com.yoyomo.global.config.jwt.JwtProvider;
import com.yoyomo.global.config.jwt.presentation.JwtResponse;
import com.yoyomo.global.config.kakao.KakaoServiceNew;
import com.yoyomo.global.config.kakao.dto.KakaoAccount;
import com.yoyomo.global.config.kakao.dto.KakaoTokenResponse;
import com.yoyomo.global.config.kakao.dto.KakaoUserInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserManageUsecase {

    private final UserGetService userGetService;
    private final UserSaveService userSaveService;
    private final KakaoServiceNew kakaoServiceNew;
    private final JwtProvider jwtProvider;
    private final UserMapper mapper;

    public UserResponseDTO.Response authenticate(String code) {
        KakaoTokenResponse tokenResponse = kakaoServiceNew.getToken(code);
        KakaoUserInfoResponse userInfo = kakaoServiceNew.getUserInfo(tokenResponse.getAccess_token());

        return registerMemberIfNew(userInfo);
    }

    public UserResponseDTO.Response registerMemberIfNew(KakaoUserInfoResponse userInfo) {
        String email = userInfo.getKakao_account().getEmail();

        if (userGetService.existsByEmail(email)) {
            return getUserResponse(email);
        }

        return registerNewUser(userInfo.getKakao_account());
    }

    private UserResponseDTO.Response registerNewUser(KakaoAccount account) {
        User user = mapper.from(account);
        userSaveService.save(user);

        JwtResponse tokenDto = new JwtResponse(
                jwtProvider.createAccessToken(user.getId(), user.getEmail()),
                jwtProvider.createRefreshToken()
        );

        return mapper.toResponseDTO(user, tokenDto);
    }

    private UserResponseDTO.Response getUserResponse(String email) {
        User user = userGetService.findByEmail(email);
        JwtResponse tokenDto = new JwtResponse(
                jwtProvider.createAccessToken(user.getId(), user.getEmail()),
                jwtProvider.createRefreshToken()
        );

        return mapper.toResponseDTO(user, tokenDto);
    }

}
