package com.yoyomo.domain.application.application.usecase;

import com.yoyomo.domain.application.application.dto.request.ApplicationVerificationRequestDto;

public interface ApplicationVerifyUsecase {
    void generate(String email);

    void verify(ApplicationVerificationRequestDto.VerificationRequest dto);

}
