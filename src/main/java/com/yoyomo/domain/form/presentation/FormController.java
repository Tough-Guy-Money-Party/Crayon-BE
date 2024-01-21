package com.yoyomo.domain.form.presentation;

import com.yoyomo.domain.form.application.dto.req.FormRequest;
import com.yoyomo.domain.form.application.dto.res.FormCreateResponse;
import com.yoyomo.domain.form.application.dto.res.FormDetailsResponse;
import com.yoyomo.domain.form.application.dto.res.FormResponse;
import com.yoyomo.domain.form.application.usecase.FormManageUseCase;
import com.yoyomo.global.config.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.yoyomo.domain.form.presentation.constant.ResponseMessage.*;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Tag(name = "FORM")
@RestController
@RequiredArgsConstructor
@RequestMapping("/form")
public class FormController {
    private final FormManageUseCase formManageUseCase;

    @GetMapping("/{formId}")
    @Operation(summary = "지원폼 상세 조회")
    public ResponseDto<FormDetailsResponse> read(@PathVariable String formId) {
        FormDetailsResponse response = formManageUseCase.read(formId);
        return ResponseDto.of(OK.value(), SUCCESS_READ.getMessage(), response);
    }

    @GetMapping("/list")
    @Operation(summary = "지원폼 목록 조회")
    public ResponseDto<List<FormResponse>> readAll() {
        List<FormResponse> responses = formManageUseCase.read();
        return ResponseDto.of(OK.value(), SUCCESS_READ.getMessage(), responses);
    }

    @PostMapping
    @Operation(summary = "지원폼 생성")
    public ResponseDto<FormCreateResponse> create(@RequestBody FormRequest request) {
        FormCreateResponse response = formManageUseCase.create(request);
        return ResponseDto.of(CREATED.value(), SUCCESS_CREATE.getMessage(), response);
    }

    @PatchMapping("/{formId}")
    @Operation(summary = "지원폼 수정")
    public ResponseDto update(@PathVariable String formId, @RequestBody FormRequest request) {
        formManageUseCase.update(formId, request);
        return ResponseDto.of(OK.value(), SUCCESS_UPDATE.getMessage());
    }

    @DeleteMapping("/{formId}")
    @Operation(summary = "지원폼 삭제")
    public ResponseDto delete(@PathVariable String formId) {
        formManageUseCase.delete(formId);
        return ResponseDto.of(OK.value(), SUCCESS_DELETE.getMessage());
    }
}
