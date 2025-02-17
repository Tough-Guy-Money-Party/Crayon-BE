package com.yoyomo.domain.landing.presentation;

import com.yoyomo.domain.landing.application.dto.request.CreateSubDomainRequest;
import com.yoyomo.domain.landing.application.dto.request.LandingRequestDTO;
import com.yoyomo.domain.landing.application.dto.request.LandingRequestDTO.NotionSave;
import com.yoyomo.domain.landing.application.dto.request.LandingRequestDTO.Style;
import com.yoyomo.domain.landing.application.dto.response.LandingResponseDTO;
import com.yoyomo.domain.landing.application.dto.response.LandingResponseDTO.All;
import com.yoyomo.domain.landing.application.dto.response.LandingResponseDTO.General;
import com.yoyomo.domain.landing.application.usecase.LandingAllSettingManageUsecase;
import com.yoyomo.domain.landing.application.usecase.LandingGeneralManageUsecase;
import com.yoyomo.domain.landing.application.usecase.LandingStyleManagementUsecase;
import com.yoyomo.domain.user.domain.entity.User;
import com.yoyomo.global.common.annotation.CurrentUser;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.UUID;

import static com.yoyomo.domain.club.presentation.constant.ResponseMessage.SUCCESS_CREATE_SUBDOMAIN;
import static com.yoyomo.domain.club.presentation.constant.ResponseMessage.SUCCESS_UPDATE;
import static com.yoyomo.domain.landing.presentation.constant.ResponseMessage.SUCCESS_READ;
import static org.springframework.http.HttpStatus.OK;

@Tag(name = "LANDING")
@RestController()
@RequestMapping("/landing")
@RequiredArgsConstructor
public class LandingController {

    private final LandingGeneralManageUsecase landingGeneralManageUsecase;
    private final LandingStyleManagementUsecase landingStyleManagementUsecase;
    private final LandingAllSettingManageUsecase landingAllSettingManageUsecase;

    @Operation(summary = "[Landing] notion 페이지 링크를 입력받아 저장합니다.")
    @PostMapping
    public ResponseDto<Void> update(@RequestBody NotionSave dto,
                                    @CurrentUser User user) {
        landingGeneralManageUsecase.update(dto, user);

        return ResponseDto.of(OK.value(), SUCCESS_UPDATE.getMessage());
    }

    @Operation(summary = "[Landing] 서브도메인을 입력받아 홍보사이트를 배포합니다.")
    @PostMapping("/subdomain/{clubId}")
    public ResponseDto<Void> create(
            @PathVariable UUID clubId,
            @RequestBody CreateSubDomainRequest request,
            @CurrentUser User user) {

        landingGeneralManageUsecase.create(user, clubId, request);
        return ResponseDto.of(OK.value(), SUCCESS_CREATE_SUBDOMAIN.getMessage());
    }

    @Operation(summary = "[Landing] 랜딩 포괄설정 조회")
    @GetMapping("/general/{clubId}")
    public ResponseDto<General> readGeneral(@PathVariable String clubId,
                                            @CurrentUser User user) {
        General response = landingGeneralManageUsecase.readGeneral(clubId, user);

        return ResponseDto.of(OK.value(), SUCCESS_READ.getMessage(), response);
    }

    @Operation(summary = "[Landing] 랜딩 포괄설정")
    @PatchMapping("/general")
    public ResponseDto<Void> updateGeneral(@RequestBody LandingRequestDTO.General dto,
                                           @CurrentUser User user) throws IOException {
        landingGeneralManageUsecase.update(dto, user);

        return ResponseDto.of(OK.value(), SUCCESS_UPDATE.getMessage());
    }

    @Operation(summary = "[Landing] 동아리 랜딩 스타일 세팅")
    @PatchMapping("/style")
    public ResponseDto<Void> update(@RequestBody Style dto,
                                    @CurrentUser User user) {
        landingStyleManagementUsecase.update(dto, user);

        return ResponseDto.of(OK.value(), SUCCESS_UPDATE.getMessage());
    }

    @Operation(summary = "[Landing] 동아리 랜딩 스타일 조회")
    @GetMapping("/style/{clubId}")
    public ResponseDto<LandingResponseDTO.Style> read(@PathVariable String clubId,
                                                      @CurrentUser User user) {
        LandingResponseDTO.Style response = landingStyleManagementUsecase.read(clubId, user);

        return ResponseDto.of(OK.value(), SUCCESS_READ.getMessage(), response);
    }

    @GetMapping("/general-for-react")
    public ResponseDto<All> readAll(@RequestParam String subDomain) {
        All response = landingAllSettingManageUsecase.readAll(subDomain);
        return ResponseDto.of(OK.value(), SUCCESS_READ.getMessage(), response);
    }

    @GetMapping("/check/{clubId}")
    @Operation(summary = "홍보페이지 유무 조회")
    public ResponseDto<Boolean> check(@PathVariable UUID clubId,
                                      @CurrentUser User user) {
        boolean response = landingAllSettingManageUsecase.check(clubId, user);
        return ResponseDto.of(OK.value(), SUCCESS_READ.getMessage(), response);
    }
}
