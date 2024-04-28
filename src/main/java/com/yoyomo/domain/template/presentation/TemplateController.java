package com.yoyomo.domain.template.presentation;

import com.yoyomo.domain.template.application.dto.req.TemplateRequest;
import com.yoyomo.domain.template.application.dto.req.TemplateUpdateRequest;
import com.yoyomo.domain.template.application.dto.res.TemplateCreateResponse;
import com.yoyomo.domain.template.application.dto.res.TemplateDetailsResponse;
import com.yoyomo.domain.template.application.dto.res.TemplateResponse;
import com.yoyomo.domain.template.application.usecase.TemplateManageUseCase;
import com.yoyomo.global.config.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.yoyomo.domain.template.presentation.constant.ResponseMessage.*;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Tag(name = "TEMPLATE")
@RestController
@RequiredArgsConstructor
@RequestMapping("/templates")
public class TemplateController {
    private final TemplateManageUseCase templateManageUseCase;

    @GetMapping("/{templateId}")
    @Operation(summary = "템플릿 상세 조회")
    public ResponseDto<TemplateDetailsResponse> read(@PathVariable String templateId) {
        TemplateDetailsResponse response = templateManageUseCase.read(templateId);
        return ResponseDto.of(OK.value(), SUCCESS_READ.getMessage(), response);
    }

    @GetMapping
    @Operation(summary = "템플릿 목록 조회")
    public ResponseDto<List<TemplateResponse>> readAll(@RequestParam String clubId) {
        List<TemplateResponse> responses = templateManageUseCase.readAll(clubId);
        return ResponseDto.of(OK.value(), SUCCESS_READ.getMessage(), responses);
    }

    @PostMapping
    @Operation(summary = "템플릿 생성")
    public ResponseDto<TemplateCreateResponse> create(@RequestBody TemplateRequest request) {
        TemplateCreateResponse response = templateManageUseCase.create(request);
        return ResponseDto.of(CREATED.value(), SUCCESS_CREATE.getMessage(), response);
    }

    @PutMapping("/{templateId}")
    @Operation(summary = "템플릿 수정")
    public ResponseDto update(@PathVariable String templateId, @RequestBody TemplateUpdateRequest request) {
        templateManageUseCase.update(templateId, request);
        return ResponseDto.of(OK.value(), SUCCESS_UPDATE.getMessage());
    }

    @DeleteMapping("/{templateId}")
    @Operation(summary = "템플릿 삭제")
    public ResponseDto delete(@PathVariable String templateId) {
        templateManageUseCase.delete(templateId);
        return ResponseDto.of(OK.value(), SUCCESS_DELETE.getMessage());
    }
}
