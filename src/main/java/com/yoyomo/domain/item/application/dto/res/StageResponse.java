package com.yoyomo.domain.item.application.dto.res;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class StageResponse extends ItemResponse {
    private String meaningOfHigh;

    private String meaningOfLow;
}
