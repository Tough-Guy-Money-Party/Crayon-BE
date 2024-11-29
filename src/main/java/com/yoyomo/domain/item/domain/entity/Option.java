package com.yoyomo.domain.item.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Option {
    private String id;
    private String title;
    private boolean selected;
}
