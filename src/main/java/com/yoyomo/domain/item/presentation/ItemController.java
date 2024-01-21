package com.yoyomo.domain.item.presentation;

import com.yoyomo.domain.item.application.dto.req.ItemRequest;
import com.yoyomo.domain.item.application.dto.res.ItemResponse;
import com.yoyomo.domain.item.application.usecase.ItemManageUseCase;
import com.yoyomo.global.config.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.yoyomo.domain.item.presentation.constant.ResponseMessage.*;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Tag(name = "ITEM")
@RestController
@RequestMapping("/form/{formId}/item")
@RequiredArgsConstructor
public class ItemController {
    private final ItemManageUseCase itemManageUseCase;

    @PostMapping
    @Operation(summary = "항목 생성")
    public ResponseDto create(@PathVariable String formId, @RequestBody ItemRequest itemRequest) {
        itemManageUseCase.create(formId, itemRequest);
        return ResponseDto.of(CREATED.value(), SUCCESS_CREATE.getMessage());
    }

    @PatchMapping("/{itemId}")
    @Operation(summary = "항목 수정")
    public ResponseDto update(@PathVariable String formId,
                              @PathVariable String itemId,
                              @RequestBody ItemRequest request) {
        itemManageUseCase.update(formId, itemId, request);
        return ResponseDto.of(OK.value(), SUCCESS_UPDATE.getMessage());
    }

    @DeleteMapping("/{itemId}")
    @Operation(summary = "항목 삭제")
    public ResponseDto delete(@PathVariable String formId, @PathVariable String itemId) {
        itemManageUseCase.delete(formId, itemId);
        return ResponseDto.of(OK.value(), SUCCESS_DELETE.getMessage());
    }

    @GetMapping("/{itemId}")
    @Operation(summary = "항목 조회")
    public ResponseDto<ItemResponse> read(@PathVariable String formId, @PathVariable String itemId) {
        ItemResponse response = itemManageUseCase.get(formId, itemId);
        return ResponseDto.of(OK.value(), SUCCESS_READ.getMessage(), response);
    }
}
