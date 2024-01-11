package com.yoyomo.global.config.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDto<T> {
    private HttpStatus code;
    private String message;
    private T data;

    public static <T> ResponseDto<T> of(HttpStatus code, String message) {
        return new ResponseDto(code, message, null);
    }

    public static <T> ResponseDto<T> of(HttpStatus code, String message, T dto) {
        return new ResponseDto(code, message, dto);
    }
}