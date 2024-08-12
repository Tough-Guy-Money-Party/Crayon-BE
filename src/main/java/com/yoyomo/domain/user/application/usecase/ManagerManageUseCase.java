package com.yoyomo.domain.user.application.usecase;

import com.yoyomo.domain.user.application.dto.ManagerDTO;
import com.yoyomo.domain.user.application.mapper.ManagerMapper;
import com.yoyomo.domain.user.domain.entity.Manager;
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
public class ManagerManageUseCase {

    private final UserGetService userGetService;
    private final UserSaveService userSaveService;
    private final KakaoServiceNew kakaoServiceNew;
    private final JwtProvider jwtProvider;
    private final ManagerMapper mapper;

    public ManagerDTO.Response authenticate(String code) {
        KakaoTokenResponse tokenResponse = kakaoServiceNew.getToken(code);
        KakaoUserInfoResponse userInfo = kakaoServiceNew.getUserInfo(tokenResponse.getAccess_token());

        return registerMemberIfNew(userInfo);
    }

    private ManagerDTO.Response registerMemberIfNew(KakaoUserInfoResponse userInfo) {
        String email = userInfo.getKakao_account().getEmail();

        if (userGetService.existsByEmail(email)) {
            return getManagerResponse(email);
        }

        return registerNewManager(userInfo.getKakao_account());
    }

    private ManagerDTO.Response registerNewManager(KakaoAccount account) {
        Manager manager = mapper.from(account);
        userSaveService.save(manager);

        JwtResponse tokenDto = new JwtResponse(
                jwtProvider.createAccessToken(manager.getId(), manager.getEmail()),
                jwtProvider.createRefreshToken()
        );

        return mapper.to(manager, tokenDto);
    }

    private ManagerDTO.Response getManagerResponse(String email) {
        Manager manager = userGetService.findByEmail(email);
        JwtResponse tokenDto = new JwtResponse(
                jwtProvider.createAccessToken(manager.getId(), manager.getEmail()),
                jwtProvider.createRefreshToken()
        );

        return mapper.to(manager, tokenDto);
    }

}
