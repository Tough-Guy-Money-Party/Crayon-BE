package com.yoyomo.domain.application.presentation;


import com.yoyomo.domain.application.application.dto.request.ApplicationStatusRequest;
import com.yoyomo.domain.application.application.dto.request.EvaluationMemoRequest;
import com.yoyomo.domain.application.application.dto.request.EvaluationRequest;
import com.yoyomo.domain.application.application.usecase.EvaluationManageUseCase;
import com.yoyomo.global.common.annotation.CurrentUser;
import com.yoyomo.global.common.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.yoyomo.domain.application.presentation.constant.ResponseMessage.SUCCESS_SAVE_EVALUATION;
import static com.yoyomo.domain.application.presentation.constant.ResponseMessage.SUCCESS_UPDATE_EVALUATION;
import static org.springframework.http.HttpStatus.OK;

@Tag(name = "EVALUATION")
@RestController
@RequiredArgsConstructor
@RequestMapping("/evaluations")
public class EvaluationController {

    private final EvaluationManageUseCase evaluationManageUseCase;

    @PatchMapping("/status")
    @Operation(summary = "합불 결과 변경")
    public ResponseDto<Void> updatedStatus(@RequestParam String applicationId,
                                           @RequestBody @Valid ApplicationStatusRequest request,
                                           @CurrentUser @Parameter(hidden = true) Long userId) {
        evaluationManageUseCase.updateStatus(applicationId, request, userId);

        return ResponseDto.of(OK.value(), SUCCESS_SAVE_EVALUATION.getMessage());
    }

    @PostMapping("/rating")
    @Operation(summary = "임원 평가 추가")
    public ResponseDto<Void> saveRating(@RequestParam String applicationId,
                                        @RequestBody @Valid EvaluationRequest request,
                                        @CurrentUser @Parameter(hidden = true) Long userId) {
        evaluationManageUseCase.saveRating(applicationId, request, userId);

        return ResponseDto.of(OK.value(), SUCCESS_UPDATE_EVALUATION.getMessage());
    }

    @PatchMapping("/rating")
    @Operation(summary = "임원 평가 변경")
    public ResponseDto<Void> updateRating(@RequestParam Long evaluationId,
                                          @RequestBody @Valid EvaluationRequest request,
                                          @CurrentUser @Parameter(hidden = true) Long userId) {
        evaluationManageUseCase.updateRating(evaluationId, request, userId);

        return ResponseDto.of(OK.value(), SUCCESS_UPDATE_EVALUATION.getMessage());
    }

    @PostMapping("/memo")
    @Operation(summary = "평가 메모 추가")
    public ResponseDto<Void> updateRating(@RequestParam String applicationId,
                                          @RequestBody @Valid EvaluationMemoRequest request,
                                          @CurrentUser @Parameter(hidden = true) Long userId) {
        evaluationManageUseCase.createMemo(applicationId, request, userId);

        return ResponseDto.of(OK.value(), SUCCESS_UPDATE_EVALUATION.getMessage());
    }
}
