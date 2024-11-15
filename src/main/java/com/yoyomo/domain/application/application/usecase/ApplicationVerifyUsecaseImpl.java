package com.yoyomo.domain.application.application.usecase;

import com.yoyomo.global.config.verify.VerificationService;
import com.yoyomo.domain.application.application.dto.request.ApplicationVerificationRequestDto;
import com.yoyomo.domain.mail.domain.service.MailVerifyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplicationVerifyUsecaseImpl implements ApplicationVerifyUsecase {
    private final VerificationService verificationService;

    private final MailVerifyService mailVerifyService;

    @Override
    public void generate(String email) {
        String code = verificationService.GenerateCode(email);
        mailVerifyService.sendVerifyCode(email, code);
    }

    @Override
    public void verify(ApplicationVerificationRequestDto.VerificationRequest dto) {
        verificationService.verifyCode(dto);
    }
}
