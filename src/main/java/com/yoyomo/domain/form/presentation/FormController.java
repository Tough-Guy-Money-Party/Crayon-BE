package com.yoyomo.domain.form.presentation;

import com.yoyomo.domain.form.application.dto.req.FormRequest;
import com.yoyomo.domain.form.application.dto.res.FormCreateResponse;
import com.yoyomo.domain.form.application.usecase.FormManageUseCase;
import com.yoyomo.global.config.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.yoyomo.domain.form.presentation.constant.ResponseMessage.SUCCESS_CREATE;
import static org.springframework.http.HttpStatus.CREATED;

@Tag(name = "FORM")
@RestController
@RequiredArgsConstructor
@RequestMapping("/form")
public class FormController {
    private final FormManageUseCase formManageUseCase;

    @PostMapping
    @Operation(summary = "지원폼 생성")
    public ResponseDto<FormCreateResponse> create(@RequestBody FormRequest request) {
        FormCreateResponse response = formManageUseCase.create(request);
        return ResponseDto.of(CREATED.value(), SUCCESS_CREATE.getMessage(), response);
    }
}
