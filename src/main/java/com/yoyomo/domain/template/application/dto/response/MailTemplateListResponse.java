package com.yoyomo.domain.template.application.dto.response;

import com.yoyomo.domain.template.domain.entity.MailTemplate;

public record MailTemplateListResponse(
        String templateId,
        String customTemplateName
) {
    public static MailTemplateListResponse of(MailTemplate mailTemplate) {
        return new MailTemplateListResponse(mailTemplate.getId().toString(), mailTemplate.getCustomTemplateName());
    }
}
