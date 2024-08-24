package com.yoyomo.domain.application.presentation;

import com.yoyomo.domain.application.application.usecase.ApplyUseCase;
import com.yoyomo.domain.user.application.dto.request.UserRequestDTO.Find;
import com.yoyomo.global.common.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.yoyomo.domain.application.application.dto.request.ApplicationRequestDTO.Save;
import static com.yoyomo.domain.application.application.dto.response.ApplicationResponseDTO.MyResponse;
import static com.yoyomo.domain.application.presentation.constant.ResponseMessage.SUCCESS_READ_ALL;
import static com.yoyomo.domain.application.presentation.constant.ResponseMessage.SUCCESS_SAVE;
import static org.springframework.http.HttpStatus.OK;

@Tag(name = "APPLICATION")
@RestController
@RequiredArgsConstructor
@RequestMapping("/applications")
public class ApplicationController {

    private final ApplyUseCase applyUseCase;

    @PostMapping("/{recruitmentId}")
    @Operation(summary = "지원서 작성")
    public ResponseDto<Void> apply(@RequestBody @Valid Save dto, @PathVariable String recruitmentId) {
        applyUseCase.apply(dto, recruitmentId);
        return ResponseDto.of(OK.value(), SUCCESS_SAVE.getMessage());
    }

    @GetMapping
    @Operation(summary = "내 지원 목록 조회")
    public ResponseDto<List<MyResponse>> readAll(@RequestBody @Valid Find dto) {
        return ResponseDto.of(OK.value(), SUCCESS_READ_ALL.getMessage(), applyUseCase.readAll(dto));
    }
}
