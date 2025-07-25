package com.yoyomo.domain.template.application.dto.request;

import jakarta.validation.constraints.NotBlank;

public record MailTemplateUpdateRequest(
	@NotBlank String customTemplateName,
	@NotBlank String subject,
	@NotBlank String htmlPart,
	@NotBlank String textPart
) {
}
