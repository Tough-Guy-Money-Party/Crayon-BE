package com.yoyomo.domain.mail.application.usecase;

import com.yoyomo.domain.mail.application.dto.request.MailReservationRequest;

public interface MailManageUseCase {

    void reserve(MailReservationRequest dto);

}
