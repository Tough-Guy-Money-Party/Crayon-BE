package com.yoyomo.domain.mail.presentation;

import com.yoyomo.domain.mail.application.dto.request.MailRequest;
import com.yoyomo.domain.mail.application.usecase.MailManageUseCaseImpl;
import com.yoyomo.global.common.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.yoyomo.domain.mail.presentation.constant.ResponseMessage.*;
import static org.springframework.http.HttpStatus.OK;

@Tag(name = "MAIL")
@RestController
@RequiredArgsConstructor
@RequestMapping("/mails")
public class MailController {

    private final MailManageUseCaseImpl mailManageUseCase;

    @PostMapping("/schedule")
    @Operation(summary = "예약 메일 발송 요청")
    public ResponseDto<String> create(@RequestBody @Valid MailRequest dto) {
        mailManageUseCase.reserve(dto);
        return ResponseDto.of(OK.value(), SCHEDULED_MAIL_UPLOAD_SUCCESS.getMessage());
    }

    @PostMapping("/direct")
    @Operation(summary = "예약 즉시 전송 요청")
    public ResponseDto<String> direct(@RequestBody @Valid MailRequest dto) {
        mailManageUseCase.direct(dto);
        return ResponseDto.of(OK.value(), DIRECT_MAIL_SEND_SUCCESS.getMessage());
    }

    @DeleteMapping("/{processId}")
    @Operation(summary = "메일 예약 취소 요청")
    public ResponseDto<String> delete(@PathVariable Long processId) {
        mailManageUseCase.cancel(processId);
        return ResponseDto.of(OK.value(), CANCEL_MAIL_SUCCESS.getMessage());
    }
}
