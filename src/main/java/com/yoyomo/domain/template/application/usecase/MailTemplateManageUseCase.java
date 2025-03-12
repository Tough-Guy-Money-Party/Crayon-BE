package com.yoyomo.domain.template.application.usecase;

import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.club.domain.service.ClubGetService;
import com.yoyomo.domain.club.domain.service.ClubManagerAuthService;
import com.yoyomo.domain.template.application.dto.request.MailTemplateSaveRequest;
import com.yoyomo.domain.template.application.dto.request.MailTemplateUpdateRequest;
import com.yoyomo.domain.template.application.dto.response.MailTemplateGetResponse;
import com.yoyomo.domain.template.application.dto.response.MailTemplateListResponse;
import com.yoyomo.domain.template.domain.entity.MailTemplate;
import com.yoyomo.domain.template.domain.service.MailTemplateDeleteService;
import com.yoyomo.domain.template.domain.service.MailTemplateGetService;
import com.yoyomo.domain.template.domain.service.MailTemplateSaveService;
import com.yoyomo.domain.template.domain.service.MailTemplateUpdateService;
import com.yoyomo.domain.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MailTemplateManageUseCase {

    private final ClubGetService clubGetService;
    private final MailTemplateSaveService mailTemplateSaveService;
    private final MailTemplateGetService mailTemplateGetService;
    private final MailTemplateUpdateService mailTemplateUpdateService;
    private final MailTemplateDeleteService mailTemplateDeleteService;
    private final ClubManagerAuthService clubManagerAuthService;

    @Transactional
    public void save(MailTemplateSaveRequest dto, UUID clubId, User user) {
        Club club = checkAuthorityByClub(clubId.toString(), user);

        mailTemplateSaveService.save(dto, club);
    }

    public Page<MailTemplateListResponse> findAll(String clubId, Pageable pageable) {
        clubGetService.find(clubId);

        return mailTemplateGetService.findAll(clubId, pageable)
                .map(MailTemplateListResponse::of);
    }

    @Cacheable(value = "mailTemplate", key = "#templateId")
    public MailTemplateGetResponse find(UUID templateId) {
        return mailTemplateGetService.findWithSes(templateId);
    }

    @Transactional
    public void update(MailTemplateUpdateRequest dto, UUID templateId, User user) {
        MailTemplate mailTemplate = checkAuthorityByMailTemplate(templateId, user);

        mailTemplateUpdateService.update(dto, mailTemplate, templateId);
    }

    @Transactional
    public void delete(UUID templateId, User user) {
        MailTemplate mailTemplate = checkAuthorityByMailTemplate(templateId, user);

        mailTemplateDeleteService.delete(mailTemplate);
    }

    private Club checkAuthorityByClub(String clubId, User manager) {
        Club club = clubGetService.find(clubId);
        clubManagerAuthService.checkAuthorization(club, manager);

        return club;
    }

    private MailTemplate checkAuthorityByMailTemplate(UUID templateId, User manager) {
        MailTemplate mailTemplate = mailTemplateGetService.findFromLocal(templateId);
        clubManagerAuthService.checkAuthorization(mailTemplate.getClub(), manager);

        return mailTemplate;
    }
}
