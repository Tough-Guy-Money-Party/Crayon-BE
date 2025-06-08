package com.yoyomo.domain.application.application.dto.request;

import com.yoyomo.domain.application.domain.vo.Reply;

public record DataRequest(
        String v
) {

    public Reply toReply() {
        return new Reply(v);
    }
}
