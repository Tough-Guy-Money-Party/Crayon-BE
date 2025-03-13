package com.yoyomo.domain.template.application.dto.response;

import com.yoyomo.domain.template.application.dto.request.MailTemplateUpdateRequest;
import com.yoyomo.domain.template.domain.entity.MailTemplate;
import lombok.Builder;
import software.amazon.awssdk.services.ses.model.GetTemplateResponse;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record MailTemplateGetResponse(
        UUID templateId,
        String customTemplateName,
        String subject,
        String htmlPart,
        String textPart,
        LocalDateTime createdAt
) {
    public static MailTemplateGetResponse of(MailTemplate mailTemplate, GetTemplateResponse response) {
        return MailTemplateGetResponse.builder()
                .templateId(mailTemplate.getId())
                .customTemplateName(mailTemplate.getCustomTemplateName())
                .subject(response.template().subjectPart())
                .htmlPart(response.template().htmlPart())
                .textPart(response.template().textPart())
                .createdAt(mailTemplate.getCreatedAt())
                .build();
    }

    public static MailTemplateGetResponse toResponse(MailTemplate mailTemplate, MailTemplateUpdateRequest request) {
        return MailTemplateGetResponse.builder()
                .templateId(mailTemplate.getId())
                .customTemplateName(mailTemplate.getCustomTemplateName())
                .subject(request.subject())
                .htmlPart(request.htmlPart())
                .textPart(request.textPart())
                .createdAt(LocalDateTime.now())
                .build();
    }
}
