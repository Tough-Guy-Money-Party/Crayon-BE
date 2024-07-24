package com.yoyomo.domain.club.presentation;


import com.yoyomo.domain.club.application.dto.req.UpdateGeneralSettingsRequest;
import com.yoyomo.domain.club.application.dto.req.UpdateStyleSettingsRequest;
import com.yoyomo.domain.club.application.usecase.ClubManageUseCaseImpl;
import com.yoyomo.domain.club.application.usecase.LandingManageUseCaseImpl;
import com.yoyomo.global.config.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import static com.yoyomo.domain.club.presentation.constant.ResponseMessage.SUCCESS_READ;
import static com.yoyomo.domain.club.presentation.constant.ResponseMessage.SUCCESS_UPDATE;
import static org.springframework.http.HttpStatus.*;

@Tag(name = "Landing")
@RestController
@RequiredArgsConstructor
@RequestMapping("/landing")
public class LandingController {
    private final LandingManageUseCaseImpl landingManageUseCase;

    @PostMapping
    @Operation(summary = "노션 링크 등록")
    public ResponseDto create(Authentication authentication, @RequestParam String notionPageLink) {
        landingManageUseCase.create(authentication.getName(), notionPageLink);
        return ResponseDto.of(OK.value(), SUCCESS_UPDATE.getMessage());
    }

    @PatchMapping("/general")
    @Operation(summary = "동아리 포괄 설정")
    public ResponseDto update(Authentication authentication, @RequestBody UpdateGeneralSettingsRequest updateGeneralSettingsRequest) {
        landingManageUseCase.update(authentication.getName(), updateGeneralSettingsRequest);
        return ResponseDto.of(OK.value(), SUCCESS_UPDATE.getMessage());
    }

    @GetMapping("/general")
    @Operation(summary = "동아리 포괄 설정 가져오기")
    public ResponseDto getGeneralSettings(Authentication authentication) {
        return ResponseDto.of(OK.value(), SUCCESS_READ.getMessage(), landingManageUseCase.getGeneralSetting(authentication.getName()));
    }

    @PatchMapping("/style")
    @Operation(summary = "동아리 스타일 설정")
    public ResponseDto update(Authentication authentication, @RequestBody UpdateStyleSettingsRequest updateStyleSettingsRequest) {
        landingManageUseCase.update(updateStyleSettingsRequest, authentication.getName());
        return ResponseDto.of(OK.value(), SUCCESS_UPDATE.getMessage());
    }

    @GetMapping
    @Operation(summary = "랜딩 스타일 불러오기")
    public ResponseDto getStyleSettings(Authentication authentication) {
        return ResponseDto.of(OK.value(), SUCCESS_READ.getMessage(),landingManageUseCase.getStyleSetting(authentication.getName()));
    }
}
