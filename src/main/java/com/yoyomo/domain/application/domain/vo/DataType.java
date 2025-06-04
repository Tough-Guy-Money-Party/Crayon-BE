package com.yoyomo.domain.application.domain.vo;

import com.yoyomo.domain.application.exception.InvalidDataType;
import com.yoyomo.global.common.util.DateFormatter;
import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.function.Function;

@AllArgsConstructor
public enum DataType {

    BOOLEAN("boolean", Reply::new),
    NUMBER("number", Reply::new),
    STRING("string", Reply::new),
    DATE("date", input -> new Reply(DateFormatter.format(input))),
    DATE_TIME("datetime", input -> new Reply(DateFormatter.format(input))),
    TIME_OF_DATE("timeofday", input -> new Reply(DateFormatter.format(input)));

    private final String type;
    private final Function<String, Reply> function;

    public static DataType match(String type) {
        return Arrays.stream(values())
                .filter(v -> v.type.equals(type.toLowerCase()))
                .findFirst()
                .orElseThrow(InvalidDataType::new);
    }

    public Reply format(String input) {
        return function.apply(input);
    }
}
