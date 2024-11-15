package com.yoyomo.domain.template.application.dto.response;

import com.yoyomo.domain.template.domain.entity.MailTemplate;

import java.util.UUID;

public record MailTemplateListResponse(
        UUID templateId,
        String customTemplateName
) {
    public static MailTemplateListResponse of(MailTemplate mailTemplate) {
        return new MailTemplateListResponse(mailTemplate.getId(), mailTemplate.getCustomTemplateName());
    }
}
