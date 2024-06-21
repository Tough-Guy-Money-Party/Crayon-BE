package com.yoyomo.domain.item.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Option {
    @Id
    private String id = ObjectId.get().toHexString();

    private String title;
    private boolean selected;

    public Option(String title, boolean selected) {
        this.title = title;
        this.selected = selected;
    }
}