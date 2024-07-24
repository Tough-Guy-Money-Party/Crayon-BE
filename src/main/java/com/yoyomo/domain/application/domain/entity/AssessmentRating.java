package com.yoyomo.domain.application.domain.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum AssessmentRating {
    LOW(0, 0, 30),
    MEDIUM(50, 31, 69),
    HIGH(100, 70, 100);

    private final Integer value;
    private final Integer low;
    private final Integer high;

    public static AssessmentRating calculate(Application application, AssessmentRating assessmentRating) {
        double average = (double) application.getAssessments().stream()
                .map(assessment -> assessment.getAssessmentRating().getValue())
                .reduce(assessmentRating.value, Integer::sum)
                / (application.getAssessments().size() + 1);

        return Arrays.stream(values())
                .filter(rating -> rating.low <= average && average <= rating.high)
                .findAny()
                .get();
    }
}
