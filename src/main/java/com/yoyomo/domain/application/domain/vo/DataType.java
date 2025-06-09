package com.yoyomo.domain.application.domain.vo;

import com.yoyomo.domain.application.exception.InvalidDataType;
import com.yoyomo.global.common.util.DateFormatter;
import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.function.Function;

@AllArgsConstructor
public enum DataType {

    BOOLEAN("boolean", Function.identity()),
    NUMBER("number", Function.identity()),
    STRING("string", Function.identity()),
    DATE("date", DateFormatter::format),
    DATE_TIME("datetime", DateFormatter::format),
    TIME_OF_DATE("timeofday", DateFormatter::format);

    private final String type;
    private final Function<String, String> function;

    public static DataType match(String type) {
        return Arrays.stream(values())
                .filter(v -> v.type.equals(type.toLowerCase()))
                .findFirst()
                .orElseThrow(InvalidDataType::new);
    }

    public String format(String input) {
        return function.apply(input);
    }
}
