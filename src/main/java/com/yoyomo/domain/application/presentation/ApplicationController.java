package com.yoyomo.domain.application.presentation;

import com.yoyomo.domain.application.application.dto.req.ApplicationRequest;
import com.yoyomo.domain.application.application.dto.res.ApplicationDetailsResponse;
import com.yoyomo.domain.application.application.dto.res.MyApplicationsResponse;
import com.yoyomo.domain.application.application.usecase.ApplyUseCase;
import com.yoyomo.domain.user.application.usecase.UserInfoUseCase;
import com.yoyomo.domain.user.domain.entity.User;
import com.yoyomo.global.config.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import static com.yoyomo.domain.application.presentation.constant.ResponseMessage.*;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Slf4j
@Tag(name = "APPLICATION")
@RestController
@RequiredArgsConstructor
@RequestMapping("/applications")
public class ApplicationController {
    private final UserInfoUseCase userInfoUseCase;
    private final ApplyUseCase applyUseCase;

    @PostMapping
    @Operation(summary = "지원서 작성")
    public ResponseDto apply(Authentication authentication, @RequestBody ApplicationRequest applicationRequest) {
        User user = userInfoUseCase.get(authentication);
        applyUseCase.create(user, applicationRequest);
        return ResponseDto.of(CREATED.value(), SUCCESS_CREATE.getMessage());
    }

    @PutMapping("/{applicationId}")
    @Operation(summary = "지원서 수정")
    public ResponseDto apply(Authentication authentication,
                             @PathVariable String applicationId,
                             @RequestBody ApplicationRequest applicationRequest) {
        User user = userInfoUseCase.get(authentication);
        applyUseCase.update(user, applicationId, applicationRequest);
        return ResponseDto.of(OK.value(), SUCCESS_UPDATE.getMessage());
    }

    @GetMapping("/{applicationId}")
    @Operation(summary = "내 지원서 상세 조회")
    public ResponseDto<ApplicationDetailsResponse> read(Authentication authentication,
                                                        @PathVariable String applicationId) {
        User user = userInfoUseCase.get(authentication);
        ApplicationDetailsResponse response = applyUseCase.read(user, applicationId);
        return ResponseDto.of(OK.value(), SUCCESS_READ.getMessage(), response);
    }

    @GetMapping()
    @Operation(summary = "내 지원서 목록 조회")
    public ResponseDto<MyApplicationsResponse> readAll(Authentication authentication) {
        User user = userInfoUseCase.get(authentication);
        MyApplicationsResponse response = applyUseCase.readAll(user);
        return ResponseDto.of(OK.value(), SUCCESS_READ.getMessage(), response);
    }
}

