package com.yoyomo.domain.item.domain.entity;

import com.yoyomo.domain.item.domain.entity.type.Type;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Answer extends Item {
    private String answer;
    private int maxLength;

    public static Item of(String title, String answer) {
        return Answer.builder()
                .type(Type.LONG_FORM)
                .title(title)
                .answer(answer)
                .build();
    }
}
