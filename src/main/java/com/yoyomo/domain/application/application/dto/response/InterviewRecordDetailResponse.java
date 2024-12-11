package com.yoyomo.domain.application.application.dto.response;

import com.yoyomo.domain.application.domain.entity.InterviewRecord;
import com.yoyomo.domain.user.domain.entity.User;
import lombok.Builder;

@Builder
public record InterviewRecordDetailResponse(
        long interviewRecordId,
        String manager,
        boolean isMine,
        String content,
        String image
) {

    public static InterviewRecordDetailResponse toResponse(InterviewRecord interviewRecord, User manager) {
        return InterviewRecordDetailResponse.builder()
                .interviewRecordId(interviewRecord.getId())
                .manager(interviewRecord.getManager().getName())
                .isMine(interviewRecord.isMine(manager))
                .content(interviewRecord.getContent())
                .image(interviewRecord.getImage())
                .build();
    }
}
