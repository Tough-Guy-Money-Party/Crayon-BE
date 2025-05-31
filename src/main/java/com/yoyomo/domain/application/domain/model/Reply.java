package com.yoyomo.domain.application.domain.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Reply {

    private static final String DATE_FORMAT = "%s-%s-%s";

    private final String value;

    public static Reply empty() {
        return new Reply("");
    }

    public boolean isEmpty() {
        return value == null || value.isEmpty();
    }

    public Reply formatDate() {
        String[] split = value.split(",");
        String formatted = String.format(DATE_FORMAT, split[0], split[1], split[2]);
        return new Reply(formatted);
    }

    public String value() {
        return value;
    }
}
