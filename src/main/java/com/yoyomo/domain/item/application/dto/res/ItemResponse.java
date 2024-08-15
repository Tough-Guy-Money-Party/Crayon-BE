package com.yoyomo.domain.item.application.dto.res;

import com.yoyomo.domain.item.domain.entity.type.Type;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class ItemResponse {
    private String id;
    private String title;
    private String description;
    private Type type;
    private int order;
    private String imageName;
    private boolean required;
}
