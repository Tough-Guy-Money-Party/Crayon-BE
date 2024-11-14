package com.yoyomo.domain.application.presentation;


import com.yoyomo.domain.application.application.dto.request.EvaluationRequestDTO.Save;
import com.yoyomo.domain.application.application.usecase.EvaluationManageUseCase;
import com.yoyomo.global.common.annotation.CurrentUser;
import com.yoyomo.global.common.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.yoyomo.domain.application.presentation.constant.ResponseMessage.SUCCESS_DELETE_EVALUATION;
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
    public ResponseDto<Void> save(@PathVariable String applicationId, @RequestBody @Valid Save dto, @CurrentUser @Parameter(hidden = true) Long userId) {
        evaluationManageUseCase.save(applicationId, dto, userId);

        return ResponseDto.of(OK.value(), SUCCESS_SAVE_EVALUATION.getMessage());
    }

    @PatchMapping("/{evaluationId}")
    @Operation(summary = "평가 수정")
    public ResponseDto<Void> update(@PathVariable Long evaluationId, @RequestBody @Valid Save dto, @CurrentUser @Parameter(hidden = true) Long userId) {
        evaluationManageUseCase.update(evaluationId, dto, userId);

        return ResponseDto.of(OK.value(), SUCCESS_UPDATE_EVALUATION.getMessage());
    }

    @DeleteMapping("/{evaluationId}")
    @Operation(summary = "평가 삭제")
    public ResponseDto<Void> delete(@PathVariable Long evaluationId, @CurrentUser @Parameter(hidden = true) Long userId) {
        evaluationManageUseCase.delete(evaluationId, userId);

        return ResponseDto.of(OK.value(), SUCCESS_DELETE_EVALUATION.getMessage());
    }
}
