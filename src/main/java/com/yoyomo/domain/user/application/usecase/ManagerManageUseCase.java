package com.yoyomo.domain.user.application.usecase;

import com.yoyomo.domain.club.application.dto.res.ClubResponse;
import com.yoyomo.domain.club.application.mapper.ClubMapper;
import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.club.exception.ClubNotFoundException;
import com.yoyomo.domain.user.application.dto.req.RefreshRequest;
import com.yoyomo.domain.user.application.dto.res.ManagerResponse;
import com.yoyomo.domain.user.domain.entity.Manager;
import com.yoyomo.domain.user.domain.service.UserGetService;
import com.yoyomo.domain.user.domain.service.UserSaveService;
import com.yoyomo.domain.user.domain.service.UserUpdateService;
import com.yoyomo.global.config.jwt.JwtProvider;
import com.yoyomo.global.config.jwt.presentation.JwtResponse;
import com.yoyomo.global.config.kakao.KakaoServiceNew;
import com.yoyomo.global.config.kakao.dto.KakaoTokenResponse;
import com.yoyomo.global.config.kakao.dto.KakaoUserInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ManagerManageUseCase {

    @Value("${is-using-refresh-token}")
    private boolean isUsingRefreshToken;

    private final UserGetService userGetService;
    private final UserSaveService userSaveService;
    private final UserUpdateService userUpdateService;

    private final KakaoServiceNew kakaoServiceNew;
    private final JwtProvider jwtProvider;

    private final ClubMapper clubMapper;

    public ManagerResponse authenticate(String code) {
        KakaoTokenResponse tokenResponse = kakaoServiceNew.getToken(code);
        KakaoUserInfoResponse userInfo = kakaoServiceNew.getUserInfo(
                tokenResponse.getAccess_token());

        return registerMemberIfNew(userInfo);
    }

    private ManagerResponse registerMemberIfNew(KakaoUserInfoResponse userInfo) {
        String email = userInfo.getKakao_account().getEmail();

        if (userGetService.existsByEmail(email)) {
            return getManagerResponse(email);
        }

        return registerNewManager(email, userInfo.getKakao_account().getProfile().getNickname());
    }

    private ManagerResponse registerNewManager(String email, String name) {
        Manager manager = Manager.builder()
                .email(email)
                .name(name)
                .build();

        userSaveService.save(manager);

        JwtResponse tokenDto = new JwtResponse(
                jwtProvider.createAccessToken(manager.getEmail()),
                jwtProvider.createRefreshToken(manager.getEmail())
        );

        return ManagerResponse.builder()
                .id(manager.getId())
                .email(manager.getEmail())
                .name(manager.getName())
                .token(tokenDto)
                .build();
    }

    private ManagerResponse getManagerResponse(String email) {
        Manager manager = userGetService.findByEmail(email);
        JwtResponse tokenDto = new JwtResponse(
                jwtProvider.createAccessToken(manager.getEmail()),
                jwtProvider.createRefreshToken(manager.getEmail())
        );
        return ManagerResponse.builder()
                .id(manager.getId())
                .email(manager.getEmail())
                .name(manager.getName())
                .token(tokenDto)
                .build();
    }


    public JwtResponse tokenRefresh(RefreshRequest request) {
        if (isUsingRefreshToken) {
            JwtResponse jwtResponse = jwtProvider.reissueToken(request.getRefreshToken(), request.getEmail());
            return jwtResponse;
        }
        return null;
    }

    public Void update(Authentication authentication) {
        return userUpdateService.updateByEmail(authentication.getName());
    }

    public Void delete(Authentication authentication) {
        return userUpdateService.deleteByEmail(authentication.getName());
    }

    public ClubResponse getFirstClub(Authentication authentication) {
        Manager manager = userGetService.findByEmail(authentication.getName());
        List<Club> clubs = manager.getClubs();
        if (clubs.isEmpty()) {
            throw new ClubNotFoundException();
        }
        return clubMapper.clubToClubResponse(clubs.get(0));
    }


}
