package com.yoyomo.domain.template.application.dto.request;

import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.template.domain.entity.MailTemplate;

import java.util.UUID;

public record MailTemplateSaveRequest(
        String customTemplateName,  // 템플릿 이름을 입력받아서 DB에 저장. SES에 저장할 때는 UUID로 저장.
        String subject,   // 메일 제목
        String htmlPart,  // html 파트
        String textPart,  // text 파트
        String clubId
) {
    public static MailTemplate of(MailTemplateSaveRequest dto, Club club){
        return MailTemplate.builder()
                .id(UUID.randomUUID())
                .customTemplateName(dto.customTemplateName)
                .club(club)
                .build();
    }
}
