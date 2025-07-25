package com.yoyomo.domain.item.application.dto.req;

import com.yoyomo.domain.item.domain.entity.Option;

public record OptionRequest(
	/**
	 * 지원서 양식 생성과 지원서 작성에 공통으로 사용됨. 추후 분리
	 */
	String id,
	String title,
	boolean selected,
	boolean isEtc,
	String content
) {

	public Option toOption() {
		return new Option(id, title, selected, isEtc, content);
	}
}
