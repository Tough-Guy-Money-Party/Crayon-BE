package com.yoyomo.domain.landing.application.dto.request;

import jakarta.validation.constraints.NotEmpty;

public record CreateSubDomainRequest(
	@NotEmpty String subDomain
) {
}
