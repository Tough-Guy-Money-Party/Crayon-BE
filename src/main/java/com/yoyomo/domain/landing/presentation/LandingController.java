package com.yoyomo.domain.landing.presentation;

import com.yoyomo.domain.landing.application.dto.request.LandingRequestDTO.NotionSave;
import com.yoyomo.domain.landing.application.dto.request.LandingRequestDTO.Style;
import com.yoyomo.domain.landing.application.dto.response.LandingResponseDTO;
import com.yoyomo.domain.landing.application.dto.response.LandingResponseDTO.General;
import com.yoyomo.domain.landing.application.usecase.LandingManageUsecaseImpl;
import com.yoyomo.global.common.dto.ResponseDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.yoyomo.domain.club.presentation.constant.ResponseMessage.SUCCESS_UPDATE;
import static com.yoyomo.domain.landing.presentation.constant.ResponseMessage.SUCCESS_READ;
import static org.springframework.http.HttpStatus.OK;

@Tag(name = "LANDING")
@RestController()
@RequestMapping("/landing")
@RequiredArgsConstructor
public class LandingController {
    private final LandingManageUsecaseImpl landingManageUsecase;

    @PostMapping()
    public ResponseDto<Void> update(@RequestBody NotionSave dto) {
        landingManageUsecase.update(dto);
        return ResponseDto.of(OK.value(), SUCCESS_UPDATE.getMessage());
    }

    @GetMapping("/general/{clubId}")
    public ResponseDto<General> readGeneral(@PathVariable String clubId) {
        return ResponseDto.of(OK.value(), SUCCESS_READ.getMessage(), landingManageUsecase.readGeneral(clubId));
    }

    @PatchMapping("/style")
    public ResponseDto<Void> update(Style dto) {
        landingManageUsecase.update(dto);
        return ResponseDto.of(OK.value(), SUCCESS_UPDATE.getMessage());
    }

    @GetMapping("/style/{clubId}")
    public ResponseDto<LandingResponseDTO.Style> read(@PathVariable String clubId) {
        return ResponseDto.of(OK.value(), SUCCESS_READ.getMessage(), landingManageUsecase.readStyle(clubId));
    }
}
