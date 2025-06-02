package com.yoyomo.domain.application.domain.model;

import com.yoyomo.domain.application.exception.InvalidDateFormat;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Reply {

    private static final String DATE_FORMAT = "%s-%s-%s";
    private static final String DATE_TIME_FORMAT = "%s-%s-%s %s:%s";

    private final String value;

    public static Reply empty() {
        return new Reply("");
    }

    public boolean isEmpty() {
        return value == null || value.isEmpty();
    }

    public Reply formatDate() {
        String[] split = value.split(",");
        if (split.length >= 5) {
            String formatted = String.format(DATE_TIME_FORMAT, split[0], split[1], split[2], split[3], split[4]);
            return new Reply(formatted);
        }
        if (split.length >= 3) {
            String formatted = String.format(DATE_FORMAT, split[0], split[1], split[2]);
            return new Reply(formatted);
        }
        throw new InvalidDateFormat();
    }

    public String value() {
        return value;
    }
}
