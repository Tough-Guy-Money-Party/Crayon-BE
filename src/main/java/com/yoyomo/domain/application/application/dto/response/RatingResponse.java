package com.yoyomo.domain.application.application.dto.response;

import com.yoyomo.domain.application.domain.entity.enums.Rating;

import java.util.Map;

public record RatingResponse(
        long low,
        long medium,
        long high
) {
    public static RatingResponse toResponse(Map<Rating, Long> ratingCount) {
        return new RatingResponse(
                ratingCount.get(Rating.LOW),
                ratingCount.get(Rating.MEDIUM),
                ratingCount.get(Rating.HIGH)
        );
    }
}
