package com.yoyomo.domain.application.application.dto.req;

public record AnswerRequest(
        String itemId,
        String question,
        String reply
) {
}
