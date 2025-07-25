package com.yoyomo.domain.mail.domain.service.strategy;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.yoyomo.domain.mail.application.dto.request.MailTransformRequest;
import com.yoyomo.domain.mail.domain.entity.Mail;

import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

@Component
public class MailUpdateStrategy implements MailStrategy {

	private LocalDateTime scheduledTime;

	@Override
	public void apply(MailTransformRequest dto) {
		Mail mail = dto.mail();
		mail.updateScheduledTime(dto.scheduledTime());
	}

	@Override
	public String getUpdateExpression() {
		return "SET #scheduledTime = :scheduledTime";
	}

	@Override
	public Map<String, String> getExpressionAttributeNames() {
		return Map.of("#scheduledTime", "scheduledTime");
	}

	@Override
	public Map<String, AttributeValue> getExpressionValues(MailTransformRequest dto) {
		Mail mail = dto.mail();

		return Map.of(
			":scheduledTime", AttributeValue.builder().s(String.valueOf(dto.scheduledTime())).build()
		);
	}

	@Override
	public boolean isSupport(Type type) {
		return type == Type.UPDATE;
	}

	@Override
	public LocalDateTime getScheduledTime() {
		return scheduledTime;
	}

	@Override
	public void setScheduledTime(LocalDateTime scheduledTime) {
		this.scheduledTime = scheduledTime;
	}
}

