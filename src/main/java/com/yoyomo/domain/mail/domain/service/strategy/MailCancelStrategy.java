package com.yoyomo.domain.mail.domain.service.strategy;

import com.yoyomo.domain.mail.application.dto.request.MailTransformDto;
import com.yoyomo.domain.mail.domain.entity.Mail;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.Map;

@Component
public class MailCancelStrategy implements MailStrategy {

    @Override
    public void apply(MailTransformDto dto) {
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
    public Map<String, AttributeValue> getExpressionValues(MailTransformDto dto) {
        Mail mail = dto.mail();

        return Map.of(
                ":status", AttributeValue.builder().s(String.valueOf(mail.getStatus())).build()
        );
    }

    @Override
    public boolean isSupport(Type type) {
        return type == Type.CANCEL;
    }
}

