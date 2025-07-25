package com.yoyomo.domain.recruitment.application.annotation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.yoyomo.domain.recruitment.application.validator.TimeCheckValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = TimeCheckValidator.class)
public @interface TimeCheck {

	String message() default "마감 시간이 시작 시간보다 이를 수 없습니다.";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
