package com.yoyomo.domain.recruitment.application.annotation;

import com.yoyomo.domain.recruitment.application.validator.PeriodCheckValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = PeriodCheckValidator.class)
public @interface PeriodCheck {

    String message() default "심사 기간이 모집 기간보다 이를 수 없습니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}