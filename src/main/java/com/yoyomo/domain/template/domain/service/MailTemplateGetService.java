package com.yoyomo.domain.template.domain.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.yoyomo.domain.template.application.dto.response.MailTemplateGetResponse;
import com.yoyomo.domain.template.domain.entity.MailTemplate;
import com.yoyomo.domain.template.domain.repository.MailTemplateRepository;
import com.yoyomo.domain.template.exception.SesTemplateException;
import com.yoyomo.domain.template.exception.TemplateNotFoundException;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.GetTemplateRequest;
import software.amazon.awssdk.services.ses.model.GetTemplateResponse;
import software.amazon.awssdk.services.ses.model.SesException;

@Service
@RequiredArgsConstructor
public class MailTemplateGetService {

	private final MailTemplateRepository mailTemplateRepository;
	private final SesClient sesClient;

	public Page<MailTemplate> findAll(String clubId, Pageable pageable) {
		return mailTemplateRepository.findAllByClubId(UUID.fromString(clubId), pageable);
	}

	public MailTemplateGetResponse findWithSes(UUID templateId) {
		MailTemplate template = mailTemplateRepository.findById(templateId)
			.orElseThrow(TemplateNotFoundException::new);
		return findFromSes(template);
	}

	public MailTemplate findFromLocal(UUID templateId) {
		return mailTemplateRepository.findById(templateId)
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
		} catch (SesException e) {
			throw new SesTemplateException(e.getMessage());
		}
	}
}
