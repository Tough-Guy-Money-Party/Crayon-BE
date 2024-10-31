package com.yoyomo.domain.mail.application.converter;

import software.amazon.awssdk.enhanced.dynamodb.AttributeConverter;
import software.amazon.awssdk.enhanced.dynamodb.AttributeValueType;
import software.amazon.awssdk.enhanced.dynamodb.EnhancedType;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeConverter implements AttributeConverter<LocalDateTime> {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    @Override
    public AttributeValue transformFrom(LocalDateTime input) {
        if (input == null) {
            return AttributeValue.builder().nul(true).build();
        }
        // UTC 기준으로 포맷
        String formatted = input.atOffset(ZoneOffset.UTC).format(FORMATTER);
        return AttributeValue.builder().s(formatted).build();
    }

    @Override
    public LocalDateTime transformTo(AttributeValue input) {
        if (input.s() == null || input.s().isEmpty()) {
            return null;
        }
        return LocalDateTime.parse(input.s(), FORMATTER);
    }

    @Override
    public AttributeValueType attributeValueType() {
        return AttributeValueType.S; // 문자열(String) 타입으로 저장
    }

    public EnhancedType<LocalDateTime> type() {
        return EnhancedType.of(LocalDateTime.class);
    }

}