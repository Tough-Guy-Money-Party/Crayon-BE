package com.yoyomo.domain.template.application.dto.request;

import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.template.domain.entity.MailTemplate;

import java.util.UUID;

public record MailTemplateSaveRequest(
        String customTemplateName,
        String subject,
        String htmlPart,
        String textPart,
        String clubId
) {
    public static MailTemplate of(MailTemplateSaveRequest dto, Club club) {
        return MailTemplate.builder()
                .id(UUID.randomUUID())
                .customTemplateName(dto.customTemplateName)
                .club(club)
                .build();
    }
}
