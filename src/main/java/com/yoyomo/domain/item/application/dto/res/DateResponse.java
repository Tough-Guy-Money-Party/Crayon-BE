package com.yoyomo.domain.item.application.dto.res;

import com.yoyomo.domain.item.domain.entity.Date;
import lombok.Getter;
import lombok.experimental.SuperBuilder;


@Getter
@SuperBuilder
public class DateResponse extends ItemResponse {
    private String answer;

    public static DateResponse toResponse(Date date) {
        return DateResponse.builder()
                .id(date.getId())
                .title(date.getTitle())
                .description(date.getDescription())
                .type(date.getType())
                .order(date.getOrder())
                .imageName(date.getImage().getName())
                .required(date.isRequired())
                .answer(date.getAnswer())
                .build();
    }
}
