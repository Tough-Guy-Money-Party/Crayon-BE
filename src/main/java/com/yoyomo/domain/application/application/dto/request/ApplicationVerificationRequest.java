package com.yoyomo.domain.application.application.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class ApplicationVerificationRequest {

	public record VerificationRequest(
		@Email String email,
		@NotBlank String code
	) {
	}
}
