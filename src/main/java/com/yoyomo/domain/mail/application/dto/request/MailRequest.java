package com.yoyomo.domain.mail.application.dto.request;

import com.yoyomo.domain.mail.domain.entity.Mail;
import com.yoyomo.domain.mail.domain.entity.enums.Status;
import com.yoyomo.domain.template.application.dto.request.MailTemplateSaveRequest;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

public record MailRequest(
        @NotNull Long processId,
        @NotNull LocalDateTime scheduledTime,
        @NotNull MailTemplateSaveRequest passTemplate,
        @NotNull MailTemplateSaveRequest failTemplate
){
    public Mail toMail(String source, String destination, Map<String, String> customData, UUID templateId) {
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
