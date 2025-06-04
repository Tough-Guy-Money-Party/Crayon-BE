package com.yoyomo.domain.application.application.dto.request;

import com.yoyomo.domain.application.domain.vo.Replies;
import com.yoyomo.domain.application.domain.vo.Reply;

import java.util.List;

public record RespondentRequest(
        List<DataRequest> c
) {

    public Replies toAnswers() {
        List<Reply> replies = c.stream()
                .map(DataRequest::toAnswer)
                .toList();
        return new Replies(replies);
    }
}
