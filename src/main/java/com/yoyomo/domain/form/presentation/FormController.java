package com.yoyomo.domain.form.presentation;


import com.yoyomo.domain.form.application.dto.request.FormRequestDTO.Update;
import com.yoyomo.domain.form.application.dto.response.FormResponseDTO.Response;
import com.yoyomo.domain.form.application.usecase.FormManageUseCase;
import com.yoyomo.global.common.annotation.CurrentUser;
import com.yoyomo.global.common.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.yoyomo.domain.form.application.dto.request.FormRequestDTO.Save;
import static com.yoyomo.domain.form.application.dto.response.FormResponseDTO.DetailResponse;
import static com.yoyomo.domain.form.application.dto.response.FormResponseDTO.SaveResponse;
import static com.yoyomo.domain.form.presentation.constant.ResponseMessage.*;
import static com.yoyomo.domain.item.presentation.constant.ResponseMessage.SUCCESS_CREATE;
import static org.springframework.http.HttpStatus.OK;

@Tag(name = "FORM")
@RestController
@RequiredArgsConstructor
@RequestMapping("/forms")
public class FormController {

    private final FormManageUseCase formManageUseCase;

    @PostMapping("/{clubId}")
    @Operation(summary = "폼 생성")    // 수정: 이미지 로직 추가
    public ResponseDto<SaveResponse> save(@RequestBody @Valid Save dto, @PathVariable String clubId, @CurrentUser @Parameter(hidden = true) Long userId) {
        return ResponseDto.of(OK.value(), SUCCESS_CREATE.getMessage(), formManageUseCase.create(dto, clubId, userId));
    }

    @GetMapping("/{formId}")
    @Operation(summary = "폼 상세 조회")
    public ResponseDto<DetailResponse> read(@PathVariable String formId) {
        return ResponseDto.of(OK.value(), SUCCESS_READ.getMessage(), formManageUseCase.read(formId));
    }

    @GetMapping("/all/{clubId}")
    @Operation(summary = "내 동아리의 폼 목록 조회")
    public ResponseDto<List<Response>> readAll(@CurrentUser @Parameter(hidden = true) Long userId, @PathVariable String clubId) {
        return ResponseDto.of(OK.value(), SUCCESS_READ.getMessage(), formManageUseCase.readAll(userId, clubId));
    }

    @PutMapping("/{formId}")
    @Operation(summary = "폼 수정")
    public ResponseDto<Void> update(@PathVariable String formId, @RequestBody @Valid Update dto, @CurrentUser @Parameter(hidden = true) Long userId) {
        formManageUseCase.update(formId, dto, userId);
        return ResponseDto.of(OK.value(), SUCCESS_UPDATE.getMessage());
    }

    @DeleteMapping("/{formId}")
    @Operation(summary = "폼 삭제")
    public ResponseDto<Void> delete(@PathVariable String formId, @CurrentUser @Parameter(hidden = true) Long userId) {
        formManageUseCase.delete(formId, userId);
        return ResponseDto.of(OK.value(), SUCCESS_DELETE.getMessage());
    }

    @GetMapping("/{clubId}/search")
    @Operation(summary = "내 동아리의 폼 목록 검색")
    public ResponseDto<List<Response>> search(@RequestParam String keyword, @PathVariable String clubId, @CurrentUser @Parameter(hidden = true) Long userId) {
        return ResponseDto.of(OK.value(), SUCCESS_SEARCH.getMessage(), formManageUseCase.search(keyword, clubId, userId));
    }
}
