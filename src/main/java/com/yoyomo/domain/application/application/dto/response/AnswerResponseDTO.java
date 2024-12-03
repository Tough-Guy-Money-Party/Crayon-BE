package com.yoyomo.domain.application.application.dto.response;

import com.yoyomo.domain.application.domain.entity.Answer;
import com.yoyomo.domain.item.domain.entity.Item;
import java.util.List;

public class AnswerResponseDTO {

    public record Response(
            String id,
            List<Item> items
    ) {
        public static Response toAnswerResponse(Answer answer) {
            return new AnswerResponseDTO.Response(
                    answer.getId(),
                    answer.getItems()
            );
        }
    }
}
