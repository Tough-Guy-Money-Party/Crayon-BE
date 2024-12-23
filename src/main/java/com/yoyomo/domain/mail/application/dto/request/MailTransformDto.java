package com.yoyomo.domain.mail.application.dto.request;

import com.yoyomo.domain.mail.domain.entity.Mail;

import java.time.LocalDateTime;

public record MailTransformDto(
        Mail mail,
        LocalDateTime scheduledTime
) {
    public static MailTransformDto of(Mail mail, LocalDateTime scheduledTime) {
        return new MailTransformDto(mail, scheduledTime);
    }
}
