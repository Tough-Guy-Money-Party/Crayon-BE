package com.yoyomo.domain.template.application.dto.response;

import com.yoyomo.domain.template.domain.entity.MailTemplate;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record MailTemplateListResponse(
        UUID templateId,
        String customTemplateName,
        LocalDateTime createdAt
) {
    public static MailTemplateListResponse of(MailTemplate mailTemplate) {
        return MailTemplateListResponse.builder()
                .templateId(mailTemplate.getId())
                .customTemplateName(mailTemplate.getCustomTemplateName())
                .createdAt(mailTemplate.getCreatedAt())
                .build();
    }
}
