package com.yoyomo.domain.recruitment.application.validator;

import static com.yoyomo.domain.recruitment.application.dto.request.ProcessRequest.*;

import com.yoyomo.domain.recruitment.application.annotation.PeriodCheck;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PeriodCheckValidator implements ConstraintValidator<PeriodCheck, Period> {

	@Override
	public void initialize(PeriodCheck constraintAnnotation) {
		ConstraintValidator.super.initialize(constraintAnnotation);
	}

	@Override
	public boolean isValid(Period period, ConstraintValidatorContext constraintValidatorContext) {
		// 심사 마감 일자가 발표 시작 일자보다 이른지 검사
		return period.evaluation().time().endAt()
			.isBefore(period.announcement().time().startAt().plusMinutes(1));
	}
}
