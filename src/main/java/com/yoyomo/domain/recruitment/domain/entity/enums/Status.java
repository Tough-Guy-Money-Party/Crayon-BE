package com.yoyomo.domain.recruitment.domain.entity.enums;

import com.yoyomo.domain.recruitment.domain.entity.Recruitment;

public enum Status {
    BEFORE,     // 모집 전 (inactive)
    SCHEDULED,  // 모집 예정됨 (active && isBefore)
    RECRUITING, // 모집 중 (active && ~ing)
    COMPLETE;   // 모집 마감 (active && isAfter)

    public static Status getStatus(Recruitment recruitment) {
        if (recruitment.getIsActive()) {
            if(recruitment.isBefore())
                return SCHEDULED;
            else if (recruitment.isAfter())
                return COMPLETE;
            else
                return RECRUITING;
        } else {
            if (recruitment.getDeletedAt() == null) {
                return BEFORE;
            } else {
                return COMPLETE;
            }
        }
    }
}
