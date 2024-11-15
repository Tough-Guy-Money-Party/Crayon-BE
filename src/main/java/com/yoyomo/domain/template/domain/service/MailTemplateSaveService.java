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

    public void save(MailTemplateSaveRequest dto, Club club) {
        MailTemplate template = dto.toMailTemplate(club);
        UUID templateId = mailTemplateRepository.save(template).getId();

        saveTemplate(dto, templateId);
    }

    private void saveTemplate(MailTemplateSaveRequest dto, UUID templateId) {

        Template template = Template.builder()
                .templateName(templateId.toString())
                .subjectPart(dto.subject())
                .textPart(dto.textPart())
                .htmlPart(dto.htmlPart())
                .build();

        CreateTemplateRequest saveRequest = CreateTemplateRequest.builder()
                .template(template)
                .build();

        try {
            sesClient.createTemplate(saveRequest);
        } catch (SesException e) {
            throw new SesTemplateException(e.getMessage());
        }
    }
}
