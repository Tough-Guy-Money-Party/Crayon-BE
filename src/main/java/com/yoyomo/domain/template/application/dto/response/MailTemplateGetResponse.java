package com.yoyomo.domain.template.application.dto.response;

import com.yoyomo.domain.template.domain.entity.MailTemplate;
import lombok.Builder;
import software.amazon.awssdk.services.ses.model.GetTemplateResponse;

@Builder
public record MailTemplateGetResponse(
        String templateId,
        String customTemplateName,
        String subject,
        String htmlPart,
        String textPart
) {
    public static MailTemplateGetResponse of(MailTemplate mailTemplate, GetTemplateResponse response) {
        return MailTemplateGetResponse.builder()
                .templateId(mailTemplate.getId().toString())
                .customTemplateName(mailTemplate.getCustomTemplateName())
                .subject(response.template().subjectPart())
                .htmlPart(response.template().htmlPart())
                .textPart(response.template().textPart())
                .build();
    }
}
