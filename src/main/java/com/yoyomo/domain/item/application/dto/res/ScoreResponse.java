package com.yoyomo.domain.item.application.dto.res;

import com.yoyomo.domain.item.domain.entity.Score;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class ScoreResponse extends ItemResponse {
    private String meaningOfHigh;
    private String meaningOfLow;
    private int score;

    public static ScoreResponse toResponse(Score score) {
        return ScoreResponse.builder()
                .id(score.getId())
                .title(score.getTitle())
                .description(score.getDescription())
                .type(score.getType())
                .order(score.getOrder())
                .required(score.isRequired())
                .meaningOfHigh(score.getMeaningOfHigh())
                .meaningOfLow(score.getMeaningOfLow())
                .score(score.getScore())
                .build();
    }
}
