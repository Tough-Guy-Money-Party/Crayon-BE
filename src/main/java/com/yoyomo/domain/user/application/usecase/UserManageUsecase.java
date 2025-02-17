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
import org.springframework.data.util.Pair;
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
    private final UserMapper userMapper;

    public UserResponseDTO.Response authenticate(String code) {
        KakaoTokenResponse tokenResponse = kakaoServiceNew.getToken(code);
        KakaoUserInfoResponse userInfo = kakaoServiceNew.getUserInfo(tokenResponse.getAccess_token());

        return registerMemberIfNew(userInfo);
    }

    public UserResponseDTO.Response reissueToken(String token) {
        Pair<Long, String> pair = validateRefreshToken(token);

        long userId = pair.getFirst();
        String refreshToken = pair.getSecond();

        User user = userGetService.find(userId);
        user.checkRefreshToken(refreshToken);

        JwtResponse tokenDto = getTokenDto(user);
        user.updateRefreshToken(tokenDto.getRefreshToken());

        return userMapper.toResponseDTO(user, tokenDto);
    }

    private UserResponseDTO.Response registerMemberIfNew(KakaoUserInfoResponse userInfo) {
        String email = userInfo.getKakao_account().getEmail();

        if (userGetService.existsByEmail(email)) {
            return getUserResponse(email);
        }

        return registerNewUser(userInfo.getKakao_account());
    }

    private UserResponseDTO.Response registerNewUser(KakaoAccount account) {
        User user = userMapper.from(account);
        userSaveService.save(user);

        JwtResponse tokenDto = getTokenDto(user);
        user.updateRefreshToken(tokenDto.getRefreshToken());

        return userMapper.toResponseDTO(user, tokenDto);
    }

    private UserResponseDTO.Response getUserResponse(String email) {
        User user = userGetService.findByEmail(email);

        JwtResponse tokenDto = getTokenDto(user);
        user.updateRefreshToken(tokenDto.getRefreshToken());

        return userMapper.toResponseDTO(user, tokenDto);
    }

    private JwtResponse getTokenDto(User user) {
        return new JwtResponse(
                jwtProvider.createAccessToken(user.getId()),
                jwtProvider.createRefreshToken(user.getId())
        );
    }

    private Pair<Long, String> validateRefreshToken(String token) {
        String refreshToken = jwtProvider.extractRefreshToken(token);
        jwtProvider.validateToken(refreshToken);

        Long userId = jwtProvider.extractId(refreshToken);

        return Pair.of(userId, refreshToken);
    }

}
