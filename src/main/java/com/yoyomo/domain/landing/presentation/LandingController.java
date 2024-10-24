package com.yoyomo.domain.landing.presentation;

import static com.yoyomo.domain.club.presentation.constant.ResponseMessage.SUCCESS_UPDATE;
import static com.yoyomo.domain.landing.presentation.constant.ResponseMessage.SUCCESS_READ;
import static org.springframework.http.HttpStatus.OK;

import com.yoyomo.domain.landing.application.dto.request.LandingRequestDTO;
import com.yoyomo.domain.landing.application.dto.request.LandingRequestDTO.NotionSave;
import com.yoyomo.domain.landing.application.dto.request.LandingRequestDTO.Style;
import com.yoyomo.domain.landing.application.dto.response.LandingResponseDTO;
import com.yoyomo.domain.landing.application.dto.response.LandingResponseDTO.General;
import com.yoyomo.domain.landing.application.usecase.LandingGeneralManageUsecaseImpl;
import com.yoyomo.domain.landing.application.usecase.LandingStyleManagementUsecaseImpl;
import com.yoyomo.global.common.dto.ResponseDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "LANDING")
@RestController()
@RequestMapping("/landing")
@RequiredArgsConstructor
public class LandingController {
    private final LandingGeneralManageUsecaseImpl landingGeneralManageUsecase;
    private final LandingStyleManagementUsecaseImpl landingStyleManagementUsecase;

    @PostMapping()
    public ResponseDto<Void> update(@RequestBody NotionSave dto) {
        landingGeneralManageUsecase.update(dto);
        return ResponseDto.of(OK.value(), SUCCESS_UPDATE.getMessage());
    }

    @GetMapping("/general/{clubId}")
    public ResponseDto<General> readGeneral(@PathVariable String clubId) {
        return ResponseDto.of(OK.value(), SUCCESS_READ.getMessage(), landingGeneralManageUsecase.readGeneral(clubId));
    }

    @PatchMapping("/general")
    public ResponseDto<Void> updateGeneral(LandingRequestDTO.General dto) throws IOException {
        landingGeneralManageUsecase.update(dto);
        return ResponseDto.of(OK.value(), SUCCESS_UPDATE.getMessage());
    }

    @PatchMapping("/style")
    public ResponseDto<Void> update(Style dto) {
        landingStyleManagementUsecase.update(dto);
        return ResponseDto.of(OK.value(), SUCCESS_UPDATE.getMessage());
    }

    @GetMapping("/style/{clubId}")
    public ResponseDto<LandingResponseDTO.Style> read(@PathVariable String clubId) {
        return ResponseDto.of(OK.value(), SUCCESS_READ.getMessage(), landingStyleManagementUsecase.read(clubId));
    }
}
