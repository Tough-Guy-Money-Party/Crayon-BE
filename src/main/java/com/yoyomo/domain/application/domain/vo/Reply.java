package com.yoyomo.domain.application.domain.vo;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
@AllArgsConstructor
public class Reply {

    private final String value;

    public static Reply empty() {
        return new Reply("");
    }

    public boolean isEmpty() {
        return value == null || value.isEmpty();
    }

    public String value() {
        return value;
    }

    public Reply format(DataType dataType) {
        return dataType.format(value);
    }
}
