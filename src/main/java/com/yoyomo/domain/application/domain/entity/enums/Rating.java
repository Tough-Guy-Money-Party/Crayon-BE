package com.yoyomo.domain.application.domain.entity.enums;

import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.application.domain.entity.Evaluation;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum Rating {
    LOW(0, 0, 30),
    MEDIUM(50, 31, 69),
    HIGH(100, 70, 100),
    PENDING(null, null, null);

    private final Integer value;
    private final Integer low;
    private final Integer high;

    public static Rating calculate(Application application) {
        double sum = (double) application.getEvaluations().stream()
                .map(assessment -> assessment.getRating().getValue())
                .reduce(0, Integer::sum);

        double average = sum / application.getEvaluations().stream()
                .filter(Evaluation::isAfterEvaluation)
                .count();

        return Arrays.stream(values())
                .filter(rating -> rating.low <= average && average <= rating.high)
                .findAny()
                .orElse(PENDING);
    }
}
