package com.yoyomo.domain.mail.application.dto;

import com.yoyomo.domain.mail.domain.entity.Mail;
import com.yoyomo.domain.mail.domain.entity.enums.CustomType;
import com.yoyomo.domain.mail.domain.entity.enums.Status;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class MailRequest {
    private static final String SOURCE_ADDRESS = "mail@crayon.land";

    public record Reserve(
            Long processId,
            LocalDateTime scheduledTime,
            String templateId,
            Set<CustomType> customType
    ) {

    }
    public static Mail toMail(MailRequest.Reserve dto, String destination, Map<String, String> customData){
        return Mail.builder()
                .id(UUID.randomUUID().toString())
                .templateId(dto.templateId())
                .customData(customData)
                .destination(destination)
                .source(SOURCE_ADDRESS)
                .scheduledTime(dto.scheduledTime())
                .status(Status.SCHEDULED.toString())
                .build();
    }
}
