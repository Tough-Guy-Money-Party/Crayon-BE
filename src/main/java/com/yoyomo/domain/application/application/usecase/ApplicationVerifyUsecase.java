package com.yoyomo.domain.application.application.usecase;

import com.yoyomo.domain.application.application.dto.request.ApplicationVerificationRequestDto;
import com.yoyomo.global.config.verify.VerificationService;
import com.yoyomo.infra.aws.ses.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplicationVerifyUseCase {

    private final VerificationService verificationService;
    private final MailService mailService;

    public void generate(String email) {
        String code = verificationService.generateCode(email);
        mailService.sendVerifyCode(email, code);
    }

    public void verify(ApplicationVerificationRequestDto.VerificationRequest dto) {
        verificationService.verifyCode(dto);
    }
}
