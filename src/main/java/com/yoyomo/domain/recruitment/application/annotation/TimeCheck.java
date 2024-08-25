package com.yoyomo.domain.recruitment.application.annotation;

import com.yoyomo.domain.recruitment.application.validator.TimeCheckValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = TimeCheckValidator.class)
public @interface TimeCheck {

    String message() default "마감 시간이 시작 시간보다 이를 수 없습니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}