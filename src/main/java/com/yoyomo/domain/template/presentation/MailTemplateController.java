package com.yoyomo.domain.template.presentation;

import com.yoyomo.domain.template.application.dto.request.MailTemplateSaveRequest;
import com.yoyomo.domain.template.application.dto.request.MailTemplateUpdateRequest;
import com.yoyomo.domain.template.application.dto.response.MailTemplateGetResponse;
import com.yoyomo.domain.template.application.dto.response.MailTemplateListResponse;
import com.yoyomo.domain.template.application.usecase.MailTemplateManageUseCase;
import com.yoyomo.global.common.annotation.CurrentUser;
import com.yoyomo.global.common.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.yoyomo.domain.template.presentation.constant.ResponseMessage.*;

@Tag(name = "MAIL_TEMPLATE")
@RestController
@RequiredArgsConstructor
@RequestMapping("/templates")
public class MailTemplateController {

    private final MailTemplateManageUseCase mailTemplateManageUseCase;

    @GetMapping("/{clubId}")
    @Operation(summary = "이메일 템플릿 목록 조회 API 입니다. 템플릿 ID와 이름만 반환합니다.")
    public ResponseDto<Page<MailTemplateListResponse>> read(@PathVariable String clubId, @RequestParam Integer page, @RequestParam Integer size) {
        Page<MailTemplateListResponse> response = mailTemplateManageUseCase.findAll(clubId, PageRequest.of(page, size));
        return ResponseDto.of(HttpStatus.OK.value(), SUCCESS_TEMPLATE_READ.getMessage(), response);
    }

    @GetMapping("/detail/{templateId}")
    @Operation(summary = "이메일 템플릿 상세 조회 API 입니다. 템플릿 ID를 이용해 템플릿 전체를 반환합니다.")
    public ResponseDto<MailTemplateGetResponse> read(@PathVariable String templateId) {
        MailTemplateGetResponse response = mailTemplateManageUseCase.find(templateId);
        return ResponseDto.of(HttpStatus.OK.value(), SUCCESS_TEMPLATE_READ.getMessage(), response);
    }

    @PostMapping
    @Operation(summary = "이메일 템플릿 저장 API 입니다.")
    public ResponseDto<String> save(MailTemplateSaveRequest dto, @CurrentUser @Parameter(hidden = true) Long userId) {
        mailTemplateManageUseCase.save(dto, userId);
        return ResponseDto.of(HttpStatus.OK.value(), SUCCESS_TEMPLATE_SAVE.getMessage());
    }

    @PatchMapping("/{templateId}")
    @Operation(summary = "이메일 템플릿 수정 API 입니다.")
    public ResponseDto<String> update(MailTemplateUpdateRequest dto, @PathVariable String templateId, @CurrentUser @Parameter(hidden = true) Long userId) {
        mailTemplateManageUseCase.update(dto, templateId, userId);
        return ResponseDto.of((HttpStatus.OK.value()), SUCCESS_TEMPLATE_UPDATE.getMessage());
    }

    @DeleteMapping("/{templateId}")
    @Operation(summary = "이메일 템플릿 삭제 API 입니다.")
    public ResponseDto<String> delete(@PathVariable String templateId, @CurrentUser @Parameter(hidden = true) Long userId) {
        mailTemplateManageUseCase.delete(templateId, userId);
        return ResponseDto.of(HttpStatus.OK.value(), SUCCESS_TEMPLATE_DELETE.getMessage());
    }
}
