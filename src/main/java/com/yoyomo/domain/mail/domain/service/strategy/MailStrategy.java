package com.yoyomo.domain.mail.domain.service.strategy;

import java.time.LocalDateTime;
import java.util.Map;

import com.yoyomo.domain.mail.application.dto.request.MailTransformRequest;

import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

public interface MailStrategy {
	void apply(MailTransformRequest dto);

	String getUpdateExpression();

	Map<String, String> getExpressionAttributeNames();

	Map<String, AttributeValue> getExpressionValues(MailTransformRequest dto);

	boolean isSupport(Type type);

	LocalDateTime getScheduledTime();

	void setScheduledTime(LocalDateTime scheduledTime);
}

