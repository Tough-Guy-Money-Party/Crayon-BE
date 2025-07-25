package com.yoyomo.domain.mail.presentation;

import java.util.UUID;

public record TestRequest(
	UUID clubId,
	int size
) {
}
