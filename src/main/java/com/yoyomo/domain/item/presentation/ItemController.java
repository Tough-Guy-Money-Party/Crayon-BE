package com.yoyomo.domain.item.presentation;

import com.yoyomo.domain.item.application.dto.ItemRequest;
import com.yoyomo.domain.item.application.usecase.ItemManageUseCase;
import com.yoyomo.global.config.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.yoyomo.domain.item.presentation.constant.ResponseMessage.SUCCESS_CREATE;
import static org.springframework.http.HttpStatus.CREATED;

@Tag(name = "ITEM")
@RestController
@RequestMapping("/item")
@RequiredArgsConstructor
public class ItemController {
    private final ItemManageUseCase itemManageUseCase;

    @PostMapping
    @Operation(summary = "항목 생성")
    public ResponseDto create(@RequestBody ItemRequest itemRequest) {
        itemManageUseCase.create(itemRequest);
        return ResponseDto.of(CREATED.value(), SUCCESS_CREATE.getMessage());
    }
}
