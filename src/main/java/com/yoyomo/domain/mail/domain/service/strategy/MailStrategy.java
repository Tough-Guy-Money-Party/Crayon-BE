package com.yoyomo.domain.mail.domain.service.strategy;

import com.yoyomo.domain.mail.application.dto.request.MailTransformDto;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.time.LocalDateTime;
import java.util.Map;

public interface MailStrategy {
    void apply(MailTransformDto dto);

    String getUpdateExpression();

    Map<String, String> getExpressionAttributeNames();

    Map<String, AttributeValue> getExpressionValues(MailTransformDto dto);

    boolean isSupport(Type type);

    void setScheduledTime(LocalDateTime scheduledTime);

    LocalDateTime getScheduledTime();
}

