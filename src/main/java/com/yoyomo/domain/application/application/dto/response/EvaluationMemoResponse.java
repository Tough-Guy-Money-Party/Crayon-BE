package com.yoyomo.domain.application.application.dto.response;

import com.yoyomo.domain.application.domain.entity.EvaluationMemo;
import lombok.Builder;

@Builder
public record EvaluationMemoResponse(
        long id,
        String managerName,
        String memo,
        boolean isMine
) {
    public static EvaluationMemoResponse toResponse(EvaluationMemo memo) {
        return EvaluationMemoResponse.builder()
                .id(memo.getId())
                .managerName(memo.getManager().getName())
                .memo(memo.getMemo())
                .build();
    }
}
