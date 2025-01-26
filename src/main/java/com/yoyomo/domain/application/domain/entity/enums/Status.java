package com.yoyomo.domain.application.domain.entity.enums;

public enum Status {

    /**
     * BEFORE_EVALUATION, PASS, FAIL, PENDING 만 남길것.
     * Process와 조합 => 각 단계별 평가상태.
     */
    BEFORE_EVALUATION,
    DOCUMENT_PASS,
    DOCUMENT_FAIL,
    FINAL_PASS,
    FINAL_FAIL,
    PENDING;

    public boolean isPass() {
        return this == DOCUMENT_PASS || this == FINAL_PASS;
    }
}
