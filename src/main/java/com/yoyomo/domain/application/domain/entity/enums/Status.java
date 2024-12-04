package com.yoyomo.domain.application.domain.entity.enums;

public enum Status {
    BEFORE_EVALUATION,
    DOCUMENT_PASS,
    DOCUMENT_FAIL,
    FINAL_PASS,
    FINAL_FAIL,
    ;

    public boolean isPass() {
        return this == DOCUMENT_PASS || this == FINAL_PASS;
    }
}
