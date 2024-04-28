package com.yoyomo.domain.template.application.dto.req;


public record TemplateRequest(
        String clubId,
        String name,

        String passText,

        String failText
) {
}