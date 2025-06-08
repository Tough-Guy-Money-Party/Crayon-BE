package com.yoyomo.domain.application.domain.vo;

import com.yoyomo.domain.application.exception.InvalidDataType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DataTypeTest {

    @DisplayName("데이터 타입을 찾는다")
    @CsvSource(value = {"dateTime", "dateTime", "Datetime", "DATETIME"})
    @ParameterizedTest
    void match(String data) {
        // when
        DataType match = DataType.match(data);

        // then
        assertThat(match).isEqualTo(DataType.DATE_TIME);
    }

    @DisplayName("데이터 타입을 올바르게 매칭한다")
    @CsvSource(value = {"date,DATE", "string,STRING"})
    @ParameterizedTest
    void matchOtherDataTypes(String input, DataType expected) {
        // when
        DataType dataType = DataType.match(input);

        // then
        assertThat(dataType).isEqualTo(expected);
    }

    @DisplayName("지원하지 않는 데이터 타입에 대해 예외를 발생시킨다")
    @CsvSource(value = {"invalid"})
    @ParameterizedTest
    void matchInvalidDataType(String invalidDataType) {
        assertThatThrownBy(() -> DataType.match(invalidDataType))
                .isInstanceOf(InvalidDataType.class);
    }
}
