package com.yoyomo.domain.item.application.dto.res;

import com.yoyomo.domain.item.domain.entity.Answer;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class AnswerResponse extends ItemResponse {
    private String answer;
    private int maxLength;

    public static AnswerResponse toResponse(Answer answer) {
        return AnswerResponse.builder()
                .id(answer.getId())
                .title(answer.getTitle())
                .description(answer.getDescription())
                .type(answer.getType())
                .order(answer.getOrder())
                .imageName(answer.getImage().getName())
                .required(answer.isRequired())
                .answer(answer.getAnswer())
                .build();
    }
}
