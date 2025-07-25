package com.yoyomo.domain.application.application.dto.request;

import java.util.List;

import com.yoyomo.domain.application.domain.vo.Replies;
import com.yoyomo.domain.application.domain.vo.Reply;

public record RespondentRequest(
	List<DataRequest> c
) {

	public Replies toReplies() {
		List<Reply> replies = c.stream()
			.map(this::toReply)
			.toList();
		return new Replies(replies);
	}

	private Reply toReply(DataRequest dataRequest) {
		if (dataRequest == null) {
			return Reply.empty();
		}
		return dataRequest.toReply();
	}
}
