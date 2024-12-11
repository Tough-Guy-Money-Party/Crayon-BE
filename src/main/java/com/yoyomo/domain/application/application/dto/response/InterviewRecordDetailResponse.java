package com.yoyomo.domain.application.application.dto.response;

import com.yoyomo.domain.application.domain.entity.InterviewRecord;
import lombok.Builder;

@Builder
public record InterviewRecordDetailResponse(
        long interviewRecordId,
        String manager,
        boolean isMine,
        String content,
        String image
) {

    public static InterviewRecordDetailResponse toResponse(InterviewRecord interviewRecord) {
        return InterviewRecordDetailResponse.builder()
                .interviewRecordId(interviewRecord.getId())
                .manager(interviewRecord.getManager().getName())
                .isMine(true)
                .content(interviewRecord.getContent())
                .image(interviewRecord.getImage())
                .build();
    }
}
