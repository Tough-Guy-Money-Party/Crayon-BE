package com.yoyomo.domain.item.domain.entity;

import com.yoyomo.domain.item.domain.entity.type.Type;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    @Id
    @Builder.Default
    private String id = ObjectId.get().toHexString();

    private String question;

    private Type type;

    private int order;

    private boolean required;
}