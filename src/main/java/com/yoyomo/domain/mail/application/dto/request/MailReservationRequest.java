package com.yoyomo.domain.mail.application.dto.request;

import com.yoyomo.domain.mail.domain.entity.Mail;
import com.yoyomo.domain.mail.domain.entity.enums.Status;
import com.yoyomo.domain.template.application.dto.request.MailTemplateUpdateRequest;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

import static com.yoyomo.domain.mail.domain.entity.enums.MailAddress.SOURCE_ADDRESS;

public record MailReservationRequest(
        Long processId,
        LocalDateTime scheduledTime,
        UUID templateId,
        MailTemplateUpdateRequest template
){
    public Mail toMail(String destination, Map<String, String> customData){
        return Mail.builder()
                .id(UUID.randomUUID().toString())
                .templateId(templateId.toString())
                .customData(customData)
                .destination(destination)
                .source(SOURCE_ADDRESS.getAddress())
                .scheduledTime(scheduledTime())
                .status(Status.SCHEDULED.toString())
                .build();
    }
}
