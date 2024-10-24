package com.yoyomo.infra.aws.ses.dto.request;

public class MailTemplateRequest {

    public record save(
            String templateName,
            String subject,
            String htmlPart,
            String textPart
            // 커스텀 타입 List
    ){}
}
