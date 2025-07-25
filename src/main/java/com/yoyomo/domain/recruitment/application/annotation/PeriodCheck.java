package com.yoyomo.domain.recruitment.application.annotation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.yoyomo.domain.recruitment.application.validator.PeriodCheckValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = PeriodCheckValidator.class)
public @interface PeriodCheck {

	String message() default "심사 기간이 모집 기간보다 이를 수 없습니다.";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
