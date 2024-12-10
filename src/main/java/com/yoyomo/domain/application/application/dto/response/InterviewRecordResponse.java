package com.yoyomo.domain.application.application.dto.response;

import com.yoyomo.domain.application.domain.entity.InterviewRecord;
import lombok.Builder;

@Builder
public record InterviewRecordResponse(

        Long interviewRecordId,
        String manager,
        boolean isMine,
        String content
) {

    public static InterviewRecordResponse toResponse(InterviewRecord interviewRecord) {
        return InterviewRecordResponse.builder()
                .interviewRecordId(interviewRecord.getId())
                .manager(interviewRecord.getManager().getName())
                .isMine(true)
                .content(interviewRecord.getContent())
                .build();
    }
}
