package com.yoyomo.domain.application.presentation;


import com.yoyomo.domain.application.application.dto.request.EvaluationRequestDTO.Save;
import com.yoyomo.domain.application.application.usecase.EvaluationManageUseCase;
import com.yoyomo.global.common.annotation.CurrentUser;
import com.yoyomo.global.common.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.yoyomo.domain.application.presentation.constant.ResponseMessage.SUCCESS_SAVE_EVALUATION;
import static com.yoyomo.domain.application.presentation.constant.ResponseMessage.SUCCESS_UPDATE_EVALUATION;
import static org.springframework.http.HttpStatus.OK;

@Tag(name = "EVALUATION")
@RestController
@RequiredArgsConstructor
@RequestMapping("/evaluations")
public class EvaluationController {

    private final EvaluationManageUseCase evaluationManageUseCase;

    @PostMapping("/{applicationId}")
    @Operation(summary = "평가 추가")
    public ResponseDto<Void> save(@PathVariable String applicationId, @RequestBody @Valid Save dto, @CurrentUser Long userId) {
        evaluationManageUseCase.save(applicationId, dto, userId);
        return ResponseDto.of(OK.value(), SUCCESS_SAVE_EVALUATION.getMessage());
    }

    // 수정
    @PatchMapping("/{evaluationId}")
    @Operation(summary = "평가 수정")
    public ResponseDto<Void> update(@PathVariable Long evaluationId, @RequestBody @Valid Save dto, @CurrentUser Long userId) {
        evaluationManageUseCase.update(evaluationId, dto, userId);
        return ResponseDto.of(OK.value(), SUCCESS_UPDATE_EVALUATION.getMessage());
    }

    // 삭제
}
