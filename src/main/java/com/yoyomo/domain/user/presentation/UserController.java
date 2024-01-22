package com.yoyomo.domain.user.presentation;

import com.yoyomo.domain.user.application.dto.req.*;
import com.yoyomo.domain.user.application.dto.res.UserResponse;
import com.yoyomo.domain.user.application.usecase.UserManageUseCase;
import com.yoyomo.global.config.dto.ResponseDto;
import com.yoyomo.global.config.jwt.presentation.JwtResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import static com.yoyomo.domain.user.presentation.constant.ResponseMessage.*;
import static org.springframework.http.HttpStatus.OK;

@Tag(name = "USER")
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserManageUseCase userManageUseCase;

    @PostMapping(value = "/register")
    @Operation(summary = "회원가입")
    public ResponseDto<UserResponse>  register(@RequestBody RegisterRequest request) throws Exception {
        UserResponse response = userManageUseCase.register(request);
        return ResponseDto.of(OK.value(), SUCCESS_REGISTER.getMessage(),response);
    }

    @GetMapping(value = "/login/{code}")
    @Operation(summary = "로그인")
    public ResponseDto<UserResponse> login(@PathVariable String code) throws Exception {
        System.out.println(code);
        UserResponse response =  userManageUseCase.login(code);
        return ResponseDto.of(OK.value(), SUCCESS_LOGIN.getMessage(), response);
    }

    @GetMapping(value = "/refresh")
    @Operation(summary = "토큰 재발급")
    public ResponseDto<JwtResponse> refresh(@RequestBody RefreshRequest request) throws Exception {
        JwtResponse jwtResponse =  userManageUseCase.tokenRefresh(request);
        return ResponseDto.of(OK.value(), SUCCESS_REFRESH.getMessage(), jwtResponse);
    }

    @DeleteMapping
    @Operation(summary = "유저 탈퇴")
    public ResponseDto<Void> delete(Authentication authentication) {
        userManageUseCase.delete(authentication);
        return ResponseDto.of(OK.value(), SUCCESS_DELETE.getMessage());
    }

    @PatchMapping
    @Operation(summary = "유저 수정")
    public ResponseDto<Void> update(Authentication authentication) {
        userManageUseCase.update(authentication);
        return ResponseDto.of(OK.value(), SUCCESS_UPDATE.getMessage());
    }
}