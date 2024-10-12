package com.yoyomo.domain.landing.presentation;

import com.yoyomo.domain.landing.application.dto.request.LandingRequestDTO;
import com.yoyomo.domain.landing.application.usecase.LandingManageUsecase;
import com.yoyomo.global.common.dto.ResponseDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.yoyomo.domain.club.presentation.constant.ResponseMessage.SUCCESS_UPDATE;
import static org.springframework.http.HttpStatus.OK;

@Tag(name = "LANDING")
@RestController()
@RequestMapping("/landing")
@RequiredArgsConstructor
public class LandingController {
    private final LandingManageUsecase landingManageUsecase;

    @PostMapping()
    public ResponseDto<Void> update(@RequestBody LandingRequestDTO.NotionSave dto) {
        landingManageUsecase.update(dto);
        return ResponseDto.of(OK.value(),SUCCESS_UPDATE.getMessage());
    }
}
