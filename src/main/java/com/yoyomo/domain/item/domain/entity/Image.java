package com.yoyomo.domain.item.domain.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class Image {

    private String name;
    private String url;
}
