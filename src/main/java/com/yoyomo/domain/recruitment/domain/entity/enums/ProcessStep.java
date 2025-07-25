package com.yoyomo.domain.recruitment.domain.entity.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ProcessStep {
	EVALUATION(1),
	MAILING(2),
	MOVING(3);

	private final int order;
}
