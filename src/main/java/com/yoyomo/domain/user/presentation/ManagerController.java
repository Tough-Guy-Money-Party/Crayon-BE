package com.yoyomo.domain.user.presentation;

import static com.yoyomo.domain.user.presentation.constant.ResponseMessage.SUCCESS_DELETE;
import static com.yoyomo.domain.user.presentation.constant.ResponseMessage.SUCCESS_GET_CLUBS;
import static com.yoyomo.domain.user.presentation.constant.ResponseMessage.SUCCESS_LOGIN;
import static com.yoyomo.domain.user.presentation.constant.ResponseMessage.SUCCESS_REFRESH;
import static com.yoyomo.domain.user.presentation.constant.ResponseMessage.SUCCESS_UPDATE;
import static org.springframework.http.HttpStatus.OK;

import com.yoyomo.domain.club.application.dto.res.ClubResponse;
import com.yoyomo.domain.user.application.dto.req.RefreshRequest;
import com.yoyomo.domain.user.application.dto.res.ManagerResponse;
import com.yoyomo.domain.user.application.usecase.ManagerManageUseCase;
import com.yoyomo.global.config.dto.ResponseDto;
import com.yoyomo.global.config.jwt.presentation.JwtResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "USER")
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class ManagerController {
    private final ManagerManageUseCase managerManageUseCase;

    @PostMapping(value = "/login/{code}")
    @Operation(summary = "카카오 로그인 및 회원가입")
    public ResponseDto<ManagerResponse> authenticate(@PathVariable String code) {
        ManagerResponse response = managerManageUseCase.authenticate(code);
        return ResponseDto.of(OK.value(), SUCCESS_LOGIN.getMessage(), response);
    }

    @PostMapping(value = "/refresh")
    @Operation(summary = "토큰 재발급")
    public ResponseDto<JwtResponse> refresh(@RequestBody RefreshRequest request) throws Exception {
        JwtResponse jwtResponse = managerManageUseCase.tokenRefresh(request);
        return ResponseDto.of(OK.value(), SUCCESS_REFRESH.getMessage(), jwtResponse);
    }

    @DeleteMapping
    @Operation(summary = "유저 탈퇴")
    public ResponseDto<Void> delete(Authentication authentication) {
        managerManageUseCase.delete(authentication);
        return ResponseDto.of(OK.value(), SUCCESS_DELETE.getMessage());
    }

    @PatchMapping
    @Operation(summary = "유저 수정")
    public ResponseDto<Void> update(Authentication authentication) {
        managerManageUseCase.update(authentication);
        return ResponseDto.of(OK.value(), SUCCESS_UPDATE.getMessage());
    }

    @GetMapping(value = "/clubs")
    @Operation(summary = "워크스페이스 조회")
    public ResponseDto<ClubResponse> getWorkSpaces(Authentication authentication){
        ClubResponse response = managerManageUseCase.getFirstClub(authentication);
        return ResponseDto.of(OK.value(), SUCCESS_GET_CLUBS.getMessage(), response);
    }


}
