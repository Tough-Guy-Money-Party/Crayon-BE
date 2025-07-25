package com.yoyomo.domain.mail.domain.service.strategy;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.yoyomo.domain.mail.application.dto.request.MailTransformRequest;
import com.yoyomo.domain.mail.domain.entity.Mail;

import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

@Component
public class MailCancelStrategy implements MailStrategy {

	private LocalDateTime scheduledTime = null;

	@Override
	public void apply(MailTransformRequest dto) {
		Mail mail = dto.mail();
		mail.cancel();
	}

	@Override
	public String getUpdateExpression() {
		return "SET #status = :status";
	}

	@Override
	public Map<String, String> getExpressionAttributeNames() {
		return Map.of("#status", "status");
	}

	@Override
	public Map<String, AttributeValue> getExpressionValues(MailTransformRequest dto) {
		Mail mail = dto.mail();

		return Map.of(
			":status", AttributeValue.builder().s(String.valueOf(mail.getStatus())).build()
		);
	}

	@Override
	public boolean isSupport(Type type) {
		return type == Type.CANCEL;
	}

	@Override
	public LocalDateTime getScheduledTime() {
		return scheduledTime;
	}

	@Override
	public void setScheduledTime(LocalDateTime scheduledTime) {
		this.scheduledTime = null;
	}
}

