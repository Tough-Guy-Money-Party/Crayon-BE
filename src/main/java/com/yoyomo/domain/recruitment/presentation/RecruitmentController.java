package com.yoyomo.domain.recruitment.presentation;

import com.yoyomo.domain.recruitment.application.dto.response.RecruitmentResponseDTO.DetailResponse;
import com.yoyomo.domain.recruitment.application.dto.response.RecruitmentResponseDTO.Response;
import com.yoyomo.domain.recruitment.application.usecase.RecruitmentManageUseCase;
import com.yoyomo.global.common.annotation.CurrentUser;
import com.yoyomo.global.common.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import static com.yoyomo.domain.recruitment.application.dto.request.RecruitmentRequestDTO.Save;
import static com.yoyomo.domain.recruitment.application.dto.request.RecruitmentRequestDTO.Update;
import static com.yoyomo.domain.recruitment.presentation.constant.ResponseMessage.*;
import static org.springframework.http.HttpStatus.OK;

@Tag(name = "RECRUITMENT")
@RestController
@RequiredArgsConstructor
@RequestMapping("/recruitments")
public class RecruitmentController {

    private final RecruitmentManageUseCase recruitmentManageUseCase;

    @PostMapping
    @Operation(summary = "모집 생성")
    public ResponseDto<Void> save(@RequestBody @Valid Save dto, @CurrentUser @Parameter(hidden = true) Long userId) {
        recruitmentManageUseCase.save(dto, userId);
        return ResponseDto.of(OK.value(), SUCCESS_SAVE.getMessage());
    }

    @GetMapping("/{recruitmentId}")
    @Operation(summary = "모집 상세 조회")
    public ResponseDto<DetailResponse> read(@PathVariable String recruitmentId) {
        return ResponseDto.of(OK.value(), SUCCESS_READ.getMessage(), recruitmentManageUseCase.read(recruitmentId));
    }

    @GetMapping
    @Operation(summary = "모집 목록 조회")
    public ResponseDto<Page<Response>> readAll(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "7") Integer size) {
        return ResponseDto.of(OK.value(), SUCCESS_READ.getMessage(), recruitmentManageUseCase.readAll(PageRequest.of(page, size)));
    }

    @PatchMapping("/{recruitmentId}")
    @Operation(summary = "모집 수정")
    public ResponseDto<Void> update(@PathVariable String recruitmentId, @RequestBody @Valid Update dto) {
        recruitmentManageUseCase.update(recruitmentId, dto);
        return ResponseDto.of(OK.value(), SUCCESS_UPDATE.getMessage());
    }

    @DeleteMapping("/{recruitmentId}")
    @Operation(summary = "모집 삭제")
    public ResponseDto<Void> delete(@PathVariable String recruitmentId) {
        recruitmentManageUseCase.delete(recruitmentId);
        return ResponseDto.of(OK.value(), SUCCESS_DELETE.getMessage());
    }
}
