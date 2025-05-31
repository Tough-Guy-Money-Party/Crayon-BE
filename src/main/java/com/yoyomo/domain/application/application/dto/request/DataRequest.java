package com.yoyomo.domain.application.application.dto.request;

import com.yoyomo.domain.application.domain.model.Reply;

public record DataRequest(
        String v
) {

    public Reply toAnswer() {
        return new Reply(v);
    }
}
