package com.yoyomo.domain.landing.presentation;

import com.yoyomo.domain.landing.application.dto.request.LandingRequestDTO;
import com.yoyomo.domain.landing.application.dto.request.LandingRequestDTO.NotionSave;
import com.yoyomo.domain.landing.application.dto.request.LandingRequestDTO.Style;
import com.yoyomo.domain.landing.application.dto.response.LandingResponseDTO;
import com.yoyomo.domain.landing.application.dto.response.LandingResponseDTO.General;
import com.yoyomo.domain.landing.application.usecase.LandingGeneralManageUsecaseImpl;
import com.yoyomo.domain.landing.application.usecase.LandingStyleManagementUsecaseImpl;
import com.yoyomo.global.common.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import static com.yoyomo.domain.club.presentation.constant.ResponseMessage.SUCCESS_UPDATE;
import static com.yoyomo.domain.landing.presentation.constant.ResponseMessage.SUCCESS_READ;
import static org.springframework.http.HttpStatus.OK;

@Tag(name = "LANDING")
@RestController()
@RequestMapping("/landing")
@RequiredArgsConstructor
public class LandingController {
    private final LandingGeneralManageUsecaseImpl landingGeneralManageUsecase;
    private final LandingStyleManagementUsecaseImpl landingStyleManagementUsecase;

    @Operation(summary = "[Landing] notion 페이지 링크를 입력받아 저장합니다.")
    @PostMapping()
    public ResponseDto<Void> update(@RequestBody NotionSave dto) {
        landingGeneralManageUsecase.update(dto);

        return ResponseDto.of(OK.value(), SUCCESS_UPDATE.getMessage());
    }

    @Operation(summary = "[Landing] 랜딩 포괄설정 조회")
    @GetMapping("/general/{clubId}")
    public ResponseDto<General> readGeneral(@PathVariable String clubId) {
        General response = landingGeneralManageUsecase.readGeneral(clubId);

        return ResponseDto.of(OK.value(), SUCCESS_READ.getMessage(), response);
    }

    @Operation(summary = "[Landing] 랜딩 포괄설정")
    @PatchMapping("/general")
    public ResponseDto<Void> updateGeneral(LandingRequestDTO.General dto) throws IOException {
        landingGeneralManageUsecase.update(dto);

        return ResponseDto.of(OK.value(), SUCCESS_UPDATE.getMessage());
    }

    @Operation(summary = "[Landing] 동아리 랜딩 스타일 세팅")
    @PatchMapping("/style")
    public ResponseDto<Void> update(Style dto) {
        landingStyleManagementUsecase.update(dto);

        return ResponseDto.of(OK.value(), SUCCESS_UPDATE.getMessage());
    }

    @Operation(summary = "[Landing] 동아리 랜딩 스타일 조회")
    @GetMapping("/style/{clubId}")
    public ResponseDto<LandingResponseDTO.Style> read(@PathVariable String clubId) {
        LandingResponseDTO.Style response = landingStyleManagementUsecase.read(clubId);
        
        return ResponseDto.of(OK.value(), SUCCESS_READ.getMessage(), response);
    }
}
