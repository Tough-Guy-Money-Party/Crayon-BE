package com.yoyomo.domain.form.presentation;


import com.yoyomo.domain.form.application.dto.req.FormRequestDTO;
import com.yoyomo.domain.form.application.dto.res.FormResponseDTO;
import com.yoyomo.domain.form.application.usecase.FormManageUseCase;
import com.yoyomo.global.common.annotation.CurrentUser;
import com.yoyomo.global.common.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.yoyomo.domain.item.presentation.constant.ResponseMessage.SUCCESS_CREATE;

@Tag(name = "FORM")
@RestController
@RequiredArgsConstructor
@RequestMapping("/forms")
public class FormController {

    private final FormManageUseCase formManageUseCase;

    @PostMapping("/{clubId}")
    @Operation(summary = "폼 생성")
    public ResponseDto<FormResponseDTO.SaveResponse> save(@RequestBody FormRequestDTO.Save dto, @PathVariable String clubId, @CurrentUser Long userId) {
        return ResponseDto.of(HttpStatus.OK.value(), SUCCESS_CREATE.getMessage(), formManageUseCase.create(dto, clubId, userId));
    }
}
