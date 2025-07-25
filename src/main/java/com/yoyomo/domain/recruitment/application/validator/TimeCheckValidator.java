package com.yoyomo.domain.recruitment.application.validator;

import static com.yoyomo.domain.recruitment.application.dto.request.ProcessRequest.*;

import com.yoyomo.domain.recruitment.application.annotation.TimeCheck;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TimeCheckValidator implements ConstraintValidator<TimeCheck, Time> {

	@Override
	public void initialize(TimeCheck constraintAnnotation) {
		ConstraintValidator.super.initialize(constraintAnnotation);
	}

	@Override
	public boolean isValid(Time time, ConstraintValidatorContext constraintValidatorContext) {
		// 마감 시간이 시작 시간보다 이른지 검사
		return time.startAt().isBefore(time.endAt().plusMinutes(1));
	}
}
