package com.yoyomo.domain.user.presentation;

import com.yoyomo.domain.user.application.dto.req.*;
import com.yoyomo.domain.user.application.dto.res.UserResponse;
import com.yoyomo.domain.user.application.usecase.UserManageUseCase;
import com.yoyomo.global.config.jwt.presentation.JwtResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Tag(name = "USER")
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserManageUseCase userManageUseCase;

    @PostMapping(value = "/register")
    @Operation(summary = "회원가입")
    public ResponseEntity<Void>  register(@RequestBody RegisterRequest request) throws Exception {
        return userManageUseCase.register(request);
    }

    @GetMapping(value = "/login")
    @Operation(summary = "로그인")
    public ResponseEntity<UserResponse> login(@RequestBody LoginRequest request) throws Exception {
        return userManageUseCase.login(request);
    }

    @PostMapping(value = "/test-register")
    @Operation(summary = "테스트 회원가입")
    public ResponseEntity<Void>  testRegister(@RequestBody TestRegisterRequest request) throws Exception {
        return userManageUseCase.testRegister(request);
    }

    @GetMapping(value = "/test-login")
    @Operation(summary = "테스트 로그인")
    public ResponseEntity<UserResponse> testLogin(@RequestBody TestLoginRequest request) throws Exception {
        return userManageUseCase.testLogin(request);
    }

    @GetMapping(value = "/refresh")
    @Operation(summary = "토큰 재발급")
    public ResponseEntity<JwtResponse> refresh(@RequestBody RefreshRequest request) throws Exception {
        return userManageUseCase.tokenRefresh(request);
    }

    @DeleteMapping
    @Operation(summary = "유저 탈퇴")
    public ResponseEntity<Void> delete(Authentication authentication) {
        return userManageUseCase.delete(authentication);
    }

    @PatchMapping
    @Operation(summary = "유저 수정")
    public ResponseEntity<Void> update(Authentication authentication) {
        return userManageUseCase.update(authentication);
    }
}