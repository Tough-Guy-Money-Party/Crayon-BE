package com.yoyomo.domain.application.domain.vo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
@AllArgsConstructor
public class Replies {

	private final List<Reply> replies;

	public int size() {
		return replies.size();
	}

	public Reply get(int index) {
		return replies.get(index);
	}
}
