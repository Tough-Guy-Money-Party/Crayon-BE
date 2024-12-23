package com.yoyomo.domain.template.application.dto.request;

import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.template.domain.entity.MailTemplate;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record MailTemplateSaveRequest(
        @NotBlank String customTemplateName,
        @NotBlank String subject,
        @NotBlank String htmlPart,
        @NotBlank String textPart
) {
    public MailTemplate toMailTemplate(Club club) {
        return MailTemplate.builder()
                .id(UUID.randomUUID())
                .customTemplateName(customTemplateName)
                .club(club)
                .build();
    }
}
