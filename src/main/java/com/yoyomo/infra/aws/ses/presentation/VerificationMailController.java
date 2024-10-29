package com.yoyomo.infra.aws.ses.presentation;

import com.yoyomo.global.common.dto.ResponseDto;
import com.yoyomo.infra.aws.ses.MailService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.yoyomo.infra.aws.ses.dto.request.MailTemplateRequest.save;
import static com.yoyomo.infra.aws.ses.presentation.constant.ResponseMessage.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/verification")
public class VerificationMailController {

    private final MailService mailService;

    @GetMapping
    @Operation(summary = "이메일 인증용 템플릿 조회 API 입니다.")
    public ResponseDto<String> read(String templateName){
        return ResponseDto.of(HttpStatus.OK.value(), SUCCESS_TEMPLATE_READ.getMessage(), mailService.getTemplate(templateName));
    }

    @PostMapping
    @Operation(summary = "이메일 인증용 템플릿 저장 API 입니다.")
    public ResponseDto<String> save(save dto){
        mailService.saveTemplate(dto);
        return ResponseDto.of(HttpStatus.OK.value(), SUCCESS_TEMPLATE_SAVE.getMessage());
    }

    @PatchMapping
    @Operation(summary = "이메일 인증용 템플릿 수정 API 입니다.")
    public ResponseDto<String> update(save dto){
        mailService.updateTemplate(dto);
        return ResponseDto.of((HttpStatus.OK.value()), SUCCESS_TEMPLATE_UPDATE.getMessage());
    }
}
