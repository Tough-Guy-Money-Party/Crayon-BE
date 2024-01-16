package com.yoyomo.domain.item.application.dto.res;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder
public class SelectResponse extends ItemResponse {
    private List<String> options;
}
