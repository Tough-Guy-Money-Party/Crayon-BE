package com.yoyomo.domain.application.application.usecase;

import org.springframework.stereotype.Service;

import com.yoyomo.domain.application.application.dto.request.ApplicationVerificationRequest;
import com.yoyomo.domain.mail.domain.service.MailVerifyService;
import com.yoyomo.global.config.verify.VerificationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApplicationVerifyUseCase {

	private final VerificationService verificationService;
	private final MailVerifyService mailVerifyService;

	public void generate(String email) {
		String code = verificationService.generateCode(email);
		mailVerifyService.sendVerifyCode(email, code);
	}

	public void verify(ApplicationVerificationRequest.VerificationRequest dto) {
		verificationService.verifyCode(dto);
	}
}
