package com.yoyomo.domain.mail.presentation;

import static com.yoyomo.domain.mail.presentation.constant.ResponseMessage.*;
import static org.springframework.http.HttpStatus.*;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yoyomo.domain.mail.application.dto.request.MailRequest;
import com.yoyomo.domain.mail.application.dto.request.MailUpdateRequest;
import com.yoyomo.domain.mail.application.usecase.MailManageUseCaseImpl;
import com.yoyomo.domain.user.domain.entity.User;
import com.yoyomo.global.common.annotation.CurrentUser;
import com.yoyomo.global.common.dto.ResponseDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "MAIL")
@RestController
@RequiredArgsConstructor
@RequestMapping("/mails")
public class MailController {

	private final MailManageUseCaseImpl mailManageUseCase;

	@PostMapping("/schedule")
	@Operation(summary = "메일 예약 발송 요청")
	public ResponseDto<String> create(@RequestBody @Valid MailRequest dto,
		@CurrentUser User user) {
		mailManageUseCase.reserve(dto, user);
		return ResponseDto.of(OK.value(), SCHEDULED_MAIL_UPLOAD_SUCCESS.getMessage());
	}

	@PostMapping("/direct")
	@Operation(summary = "메일 즉시 전송 요청")
	public ResponseDto<String> direct(@RequestBody @Valid MailRequest dto,
		@CurrentUser User user) {
		mailManageUseCase.direct(dto, user);
		return ResponseDto.of(OK.value(), DIRECT_MAIL_SEND_SUCCESS.getMessage());
	}

	@PatchMapping("/{processId}")
	@Operation(summary = "메일 예약 시간 수정")
	public ResponseDto<String> update(@PathVariable Long processId,
		@RequestBody @Valid MailUpdateRequest dto,
		@CurrentUser User user) {
		mailManageUseCase.update(processId, dto, user);
		return ResponseDto.of(OK.value(), UPDATE_MAIL_SUCCESS.getMessage());
	}

	@DeleteMapping("/{processId}")
	@Operation(summary = "메일 예약 취소 요청")
	public ResponseDto<String> delete(@PathVariable Long processId,
		@CurrentUser User user) {
		mailManageUseCase.cancel(processId, user);
		return ResponseDto.of(OK.value(), CANCEL_MAIL_SUCCESS.getMessage());
	}
}
