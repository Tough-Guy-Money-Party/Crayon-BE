package com.yoyomo.domain.template.domain.service;

import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.template.application.dto.request.MailTemplateSaveRequest;
import com.yoyomo.domain.template.domain.entity.MailTemplate;
import com.yoyomo.domain.template.domain.repository.MailTemplateRepository;
import com.yoyomo.domain.template.exception.SesTemplateException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.CreateTemplateRequest;
import software.amazon.awssdk.services.ses.model.SesException;
import software.amazon.awssdk.services.ses.model.Template;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MailTemplateSaveService {

    private final MailTemplateRepository mailTemplateRepository;
    private final SesClient sesClient;

    public void save(MailTemplateSaveRequest dto, String htmlPart, Club club) {
        MailTemplate template = dto.toMailTemplate(club);
        UUID templateId = mailTemplateRepository.save(template).getId();

        uploadTemplate(dto, htmlPart, templateId);
    }

    public UUID uploadTemplate(MailTemplateSaveRequest dto, String htmlPart) {
        UUID templateId = UUID.randomUUID();
        uploadTemplate(dto, htmlPart ,templateId);
        return templateId;
    }

    private void uploadTemplate(MailTemplateSaveRequest dto, String htmlPart, UUID templateId) {
        CreateTemplateRequest request = buildRequest(dto, htmlPart, templateId);

        try {
            sesClient.createTemplate(request);
        } catch (SesException e) {
            throw new SesTemplateException(e.getMessage());
        }
    }

    private CreateTemplateRequest buildRequest(MailTemplateSaveRequest dto, String htmlPart, UUID templateId) {
        Template template = Template.builder()
                .templateName(templateId.toString())
                .subjectPart(dto.subject())
                .textPart(dto.textPart())
                .htmlPart(htmlPart)
                .build();

        return CreateTemplateRequest.builder()
                .template(template)
                .build();
    }
}
