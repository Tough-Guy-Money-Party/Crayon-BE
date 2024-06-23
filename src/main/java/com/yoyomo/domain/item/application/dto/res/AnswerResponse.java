package com.yoyomo.domain.item.application.dto.res;

import com.yoyomo.domain.application.domain.entity.Answer;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class AnswerResponse extends ItemResponse{
    private String answer;
    private int maxLength;
}
