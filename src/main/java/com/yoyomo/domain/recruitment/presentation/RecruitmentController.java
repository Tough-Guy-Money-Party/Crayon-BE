package com.yoyomo.domain.recruitment.presentation;

import com.yoyomo.domain.recruitment.application.dto.req.RecruitmentRequest;
import com.yoyomo.domain.recruitment.application.dto.res.RecruitmentDetailsResponse;
import com.yoyomo.domain.recruitment.application.dto.res.RecruitmentResponse;
import com.yoyomo.domain.recruitment.application.usecase.RecruitmentManageUseCase;
import com.yoyomo.global.config.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.yoyomo.domain.recruitment.presentation.constant.ResponseMessage.*;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Tag(name = "RECRUITMENT")
@RestController
@RequiredArgsConstructor
@RequestMapping("/form/{formId}/recruitment")
public class RecruitmentController {
    private final RecruitmentManageUseCase recruitmentManageUseCase;

    @PostMapping
    @Operation(summary = "모집 생성")
    public ResponseDto create(@PathVariable String formId, @RequestBody RecruitmentRequest request) {
        recruitmentManageUseCase.create(formId, request);
        return ResponseDto.of(CREATED.value(), SUCCESS_CREATE.getMessage());
    }

    @GetMapping("/{recruitmentId}")
    @Operation(summary = "모집 상세 조회")
    public ResponseDto<RecruitmentDetailsResponse> read(@PathVariable String formId, @PathVariable String recruitmentId) {
        RecruitmentDetailsResponse response = recruitmentManageUseCase.read(recruitmentId);
        return ResponseDto.of(OK.value(), SUCCESS_READ.getMessage(), response);
    }

    @GetMapping("/list")
    @Operation(summary = "모집 목록 조회")
    public ResponseDto<List<RecruitmentResponse>> readAll(@PathVariable String formId) {
        List<RecruitmentResponse> responses = recruitmentManageUseCase.readAll(formId);
        return ResponseDto.of(OK.value(), SUCCESS_READ.getMessage(), responses);
    }

    @PatchMapping("/{recruitmentId}")
    @Operation(summary = "모집 수정")
    public ResponseDto update(@PathVariable String formId,
                              @PathVariable String recruitmentId,
                              @RequestBody RecruitmentRequest request) {
        recruitmentManageUseCase.update(recruitmentId, request);
        return ResponseDto.of(OK.value(), SUCCESS_UPDATE.getMessage());
    }

    @DeleteMapping("/{recruitmentId}")
    @Operation(summary = "모집 삭제")
    public ResponseDto update(@PathVariable String formId, @PathVariable String recruitmentId) {
        recruitmentManageUseCase.delete(recruitmentId);
        return ResponseDto.of(OK.value(), SUCCESS_DELETE.getMessage());
    }
}
