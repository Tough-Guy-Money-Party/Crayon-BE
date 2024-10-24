package com.yoyomo.domain.application.application.usecase;

import com.yoyomo.global.config.verify.VerificationService;
import com.yoyomo.domain.application.application.dto.request.ApplicationVerificationRequestDto;
import com.yoyomo.infra.aws.ses.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplicationVerifyUsecaseImpl implements ApplicationVerifyUsecase {
    private final VerificationService verificationService;

    private final MailService mailService;

    @Override
    public void generate(String email) {
        String code = verificationService.GenerateCode(email);
        mailService.sendVerifyCode(email, code);
    }

    @Override
    public void verify(ApplicationVerificationRequestDto.VerificationRequest dto) {
        verificationService.verifyCode(dto);
    }
}
