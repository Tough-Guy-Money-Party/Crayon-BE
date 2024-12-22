package com.yoyomo.domain.mail.presentation;

import com.yoyomo.domain.mail.application.dto.request.MailRequest;
import com.yoyomo.domain.mail.application.usecase.MailManageUseCaseImpl;
import com.yoyomo.global.common.annotation.CurrentUser;
import com.yoyomo.global.common.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
    @Operation(summary = "메일 예약 발송 요청")
    public ResponseDto<String> create(@RequestBody @Valid MailRequest dto,
                                      @CurrentUser @Parameter(hidden = true) Long userId) {
        mailManageUseCase.reserve(dto, userId);
        return ResponseDto.of(OK.value(), SCHEDULED_MAIL_UPLOAD_SUCCESS.getMessage());
    }

    @PostMapping("/direct")
    @Operation(summary = "메일 즉시 전송 요청")
    public ResponseDto<String> direct(@RequestBody @Valid MailRequest dto,
                                      @CurrentUser @Parameter(hidden = true) Long userId) {
        mailManageUseCase.direct(dto, userId);
        return ResponseDto.of(OK.value(), DIRECT_MAIL_SEND_SUCCESS.getMessage());
    }

    @DeleteMapping("/{processId}")
    @Operation(summary = "메일 예약 취소 요청")
    public ResponseDto<String> delete(@PathVariable Long processId,
                                      @CurrentUser @Parameter(hidden = true) Long userId) {
        mailManageUseCase.cancel(processId, userId);
        return ResponseDto.of(OK.value(), CANCEL_MAIL_SUCCESS.getMessage());
    }
}
