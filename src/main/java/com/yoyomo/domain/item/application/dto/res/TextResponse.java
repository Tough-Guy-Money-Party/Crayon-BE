package com.yoyomo.domain.item.application.dto.res;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class TextResponse extends ItemResponse {
    private int limit;
}
