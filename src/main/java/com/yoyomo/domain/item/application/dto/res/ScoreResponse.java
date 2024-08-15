package com.yoyomo.domain.item.application.dto.res;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class ScoreResponse extends ItemResponse {
    private String meaningOfHigh;
    private String meaningOfLow;
    private int score;
}
