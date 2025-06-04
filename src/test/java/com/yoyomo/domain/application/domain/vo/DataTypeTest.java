package com.yoyomo.domain.application.domain.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class DataTypeTest {

    @DisplayName("데이터 타입을 찾는다")
    @CsvSource(value = {"dateTime", "dateTime", "Datetime", "DateTime"})
    @ParameterizedTest
    void match(String data) {
        // when
        DataType match = DataType.match(data);

        // then
        assertThat(match).isEqualTo(DataType.DATE_TIME);
    }
}
