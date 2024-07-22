package com.yoyomo.domain.club.presentation;


import com.yoyomo.domain.club.application.dto.req.UpdateGeneralSettingsRequest;
import com.yoyomo.domain.club.application.usecase.ClubManageUseCaseImpl;
import com.yoyomo.global.config.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import static com.yoyomo.domain.club.presentation.constant.ResponseMessage.SUCCESS_UPDATE;
import static org.springframework.http.HttpStatus.*;

@Tag(name = "Landing")
@RestController
@RequiredArgsConstructor
@RequestMapping("/landing")
public class LandingController {
    private final ClubManageUseCaseImpl clubManageUseCase;

    @PostMapping
    @Operation(summary = "노션 링크 등록")
    public ResponseDto create(Authentication authentication, @RequestParam String notionPageLink) {
        clubManageUseCase.create(authentication,notionPageLink);
        return ResponseDto.of(OK.value(), SUCCESS_UPDATE.getMessage());
    }

    @PatchMapping
    @Operation(summary = "동아리 포괄설정")
    public ResponseDto update(Authentication authentication, @RequestBody UpdateGeneralSettingsRequest updateGeneralSettingsRequest) {
        clubManageUseCase.update(authentication,updateGeneralSettingsRequest);
        return ResponseDto.of(OK.value(), SUCCESS_UPDATE.getMessage());
    }
}
