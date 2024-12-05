package com.yoyomo.domain.template.domain.service;

import com.yoyomo.domain.template.application.dto.request.MailTemplateUpdateRequest;
import com.yoyomo.domain.template.application.util.HtmlSanitizer;
import com.yoyomo.domain.template.domain.entity.MailTemplate;
import com.yoyomo.domain.template.exception.SesTemplateException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.SesException;
import software.amazon.awssdk.services.ses.model.Template;
import software.amazon.awssdk.services.ses.model.UpdateTemplateRequest;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MailTemplateUpdateService {

    private final SesClient sesClient;
    private final HtmlSanitizer htmlSanitizer;

    public void update(MailTemplateUpdateRequest dto, MailTemplate mailTemplate, UUID templateId) {
        mailTemplate.update(dto);
        updateTemplate(dto, templateId);
    }

    private void updateTemplate(MailTemplateUpdateRequest dto, UUID templateId) {
        String sanitizedHtmlPart = htmlSanitizer.sanitize(dto.htmlPart());

        Template template = Template.builder()
                .templateName(templateId.toString())
                .subjectPart(dto.subject())
                .textPart(dto.textPart())
                .htmlPart(sanitizedHtmlPart)
                .build();

        UpdateTemplateRequest updateRequest = UpdateTemplateRequest.builder()
                .template(template)
                .build();

        try {
            sesClient.updateTemplate(updateRequest);
        } catch (SesException e) {
            throw new SesTemplateException(e.getMessage());
        }
    }
}
