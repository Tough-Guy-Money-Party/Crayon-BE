package com.yoyomo.domain.template.domain.service;

import com.yoyomo.domain.template.domain.entity.MailTemplate;
import com.yoyomo.domain.template.domain.repository.MailTemplateRepository;
import com.yoyomo.domain.template.exception.SesTemplateException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.DeleteTemplateRequest;

@Service
@RequiredArgsConstructor
public class MailTemplateDeleteService {

    private final MailTemplateRepository mailTemplateRepository;
    private final SesClient sesClient;

    public void delete(MailTemplate mailTemplate) {
        mailTemplateRepository.deleteById(mailTemplate.getId());
        deleteTemplate(mailTemplate);
    }

    private void deleteTemplate(MailTemplate mailTemplate) {
        DeleteTemplateRequest deleteTemplateRequest = DeleteTemplateRequest.builder()
                .templateName(mailTemplate.getId().toString())
                .build();

        try {
            sesClient.deleteTemplate(deleteTemplateRequest);
        } catch (Exception e) {
            throw new SesTemplateException(e.getMessage());
        }
    }
}
