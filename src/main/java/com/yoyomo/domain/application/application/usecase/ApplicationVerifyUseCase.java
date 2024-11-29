package com.yoyomo.domain.application.application.usecase;

import com.yoyomo.domain.application.application.dto.request.ApplicationVerificationRequestDto;
import com.yoyomo.domain.mail.domain.service.MailVerifyService;
import com.yoyomo.global.config.verify.VerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplicationVerifyUseCase {

    private final VerificationService verificationService;
    private final MailVerifyService mailVerifyService;

    public void generate(String email) {
        String code = verificationService.generateCode(email);
        mailVerifyService.sendVerifyCode(email, code);
    }

    public void verify(ApplicationVerificationRequestDto.VerificationRequest dto) {
        verificationService.verifyCode(dto);
    }
}
