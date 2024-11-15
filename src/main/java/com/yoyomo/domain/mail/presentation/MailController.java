package com.yoyomo.domain.mail.presentation;

import com.yoyomo.domain.mail.application.dto.request.MailReservationRequest;
import com.yoyomo.domain.mail.application.usecase.MailManageUseCase;
import com.yoyomo.global.common.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.yoyomo.domain.mail.presentation.constant.ResponseMessage.SCHEDULE_SAVE_SUCCESS;

@Tag(name = "MAIL")
@RestController
@RequiredArgsConstructor
@RequestMapping("/mails")
public class MailController {

    private final MailManageUseCase mailManageUseCase;

    @PostMapping("/schedule")
    @Operation(summary = "예약 메일 발송 요청")
    public ResponseDto<String> create(@RequestBody MailReservationRequest dto){
        mailManageUseCase.reserve(dto);
        return ResponseDto.of(HttpStatus.OK.value(), SCHEDULE_SAVE_SUCCESS.getMessage());
    }

}
