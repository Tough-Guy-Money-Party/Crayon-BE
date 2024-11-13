package com.yoyomo.domain.template.application.dto.request;

public record MailTemplateUpdateRequest(
        String customTemplateName,
        String subject,
        String htmlPart,
        String textPart
) {
}
