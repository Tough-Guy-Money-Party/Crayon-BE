package com.yoyomo.domain.mail.application.dto.request;

import java.time.LocalDateTime;

import com.yoyomo.domain.mail.domain.entity.Mail;

public record MailTransformRequest(
	Mail mail,
	LocalDateTime scheduledTime
) {
	public static MailTransformRequest of(Mail mail, LocalDateTime scheduledTime) {
		return new MailTransformRequest(mail, scheduledTime);
	}
}
