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
                ratingCount.getOrDefault(Rating.LOW, 0L),
                ratingCount.getOrDefault(Rating.MEDIUM, 0L),
                ratingCount.getOrDefault(Rating.HIGH, 0L)
        );
    }
}
