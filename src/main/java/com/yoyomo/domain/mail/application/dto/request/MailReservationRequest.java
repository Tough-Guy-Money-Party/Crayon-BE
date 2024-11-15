package com.yoyomo.domain.mail.application.dto.request;

import com.yoyomo.domain.mail.domain.entity.Mail;
import com.yoyomo.domain.mail.domain.entity.enums.Status;
import com.yoyomo.domain.template.application.dto.request.MailTemplateUpdateRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

public record MailReservationRequest(
        @NotBlank Long processId,
        @NotNull LocalDateTime scheduledTime,
        @NotBlank UUID templateId,
        @NotNull MailTemplateUpdateRequest template
){
    public Mail toMail(String source, String destination, Map<String, String> customData){
        return Mail.builder()
                .id(UUID.randomUUID().toString())
                .templateId(templateId.toString())
                .customData(customData)
                .source(source)
                .destination(destination)
                .scheduledTime(scheduledTime())
                .status(Status.SCHEDULED.toString())
                .build();
    }
}
