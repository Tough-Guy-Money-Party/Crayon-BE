package com.yoyomo.domain.mail.application.usecase;

import static com.yoyomo.domain.mail.application.dto.MailRequest.Reserve;

public interface MailManageUseCase {

    void reserve(Reserve dto);
}
