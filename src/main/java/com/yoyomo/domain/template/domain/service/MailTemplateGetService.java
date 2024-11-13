package com.yoyomo.domain.template.domain.service;

import com.yoyomo.domain.template.application.dto.response.MailTemplateGetResponse;
import com.yoyomo.domain.template.domain.entity.MailTemplate;
import com.yoyomo.domain.template.domain.repository.MailTemplateRepository;
import com.yoyomo.domain.template.exception.SesTemplateException;
import com.yoyomo.domain.template.exception.TemplateNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.GetTemplateRequest;
import software.amazon.awssdk.services.ses.model.GetTemplateResponse;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MailTemplateGetService {

    private final MailTemplateRepository mailTemplateRepository;
    private final SesClient sesClient;

    public Page<MailTemplate> findAll(String clubId, Pageable pageable) {
        UUID clubUUID = UUID.fromString(clubId);
        return mailTemplateRepository.findAllByClubId(clubUUID, pageable);
    }

    public MailTemplateGetResponse findWithSes(String templateId) {
        MailTemplate template = mailTemplateRepository.findById(UUID.fromString(templateId))
                .orElseThrow(TemplateNotFoundException::new);
        return findFromSes(template);
    }

    public MailTemplate findFromLocal(String templateId) {
        return mailTemplateRepository.findById(UUID.fromString(templateId))
                .orElseThrow(TemplateNotFoundException::new);
    }

    private MailTemplateGetResponse findFromSes(MailTemplate mailTemplate) {
        UUID templateId = mailTemplate.getId();

        GetTemplateRequest getRequest = GetTemplateRequest.builder()
                .templateName(templateId.toString())
                .build();

        try {
            GetTemplateResponse response = sesClient.getTemplate(getRequest);
            return MailTemplateGetResponse.of(mailTemplate, response);
        } catch (Exception e) {
            throw new SesTemplateException(e.getMessage());
        }
    }
}
