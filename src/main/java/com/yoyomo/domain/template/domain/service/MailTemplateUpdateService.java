package com.yoyomo.domain.template.domain.service;

import com.yoyomo.domain.template.application.dto.request.MailTemplateUpdateRequest;
import com.yoyomo.domain.template.domain.entity.MailTemplate;
import com.yoyomo.domain.template.exception.SesTemplateException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.Template;
import software.amazon.awssdk.services.ses.model.UpdateTemplateRequest;

@Service
@RequiredArgsConstructor
public class MailTemplateUpdateService {

    private final SesClient sesClient;

    public void update(MailTemplateUpdateRequest dto, MailTemplate mailTemplate, String templateId) {
        mailTemplate.update(dto);
        updateTemplate(dto, templateId);
    }

    private void updateTemplate(MailTemplateUpdateRequest dto, String templateId) {
        Template template = Template.builder()
                .templateName(templateId)
                .subjectPart(dto.subject())
                .textPart(dto.textPart())
                .htmlPart(dto.htmlPart())
                .build();

        UpdateTemplateRequest updateRequest = UpdateTemplateRequest.builder()
                .template(template)
                .build();

        try {
            sesClient.updateTemplate(updateRequest);
        } catch (Exception e) {
            throw new SesTemplateException(e.getMessage());
        }
    }
}
