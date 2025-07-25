package com.yoyomo.domain.application.presentation;

import static com.yoyomo.domain.application.presentation.constant.ResponseMessage.*;
import static org.springframework.http.HttpStatus.*;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yoyomo.domain.application.application.dto.request.ApplicationStatusRequest;
import com.yoyomo.domain.application.application.dto.request.EvaluationMemoRequest;
import com.yoyomo.domain.application.application.dto.request.EvaluationRequest;
import com.yoyomo.domain.application.application.dto.response.EvaluationResponses;
import com.yoyomo.domain.application.application.usecase.EvaluationManageUseCase;
import com.yoyomo.domain.user.domain.entity.User;
import com.yoyomo.global.common.annotation.CurrentUser;
import com.yoyomo.global.common.dto.ResponseDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "EVALUATION")
@RestController
@RequiredArgsConstructor
@RequestMapping("/evaluations")
public class EvaluationController {

	private final EvaluationManageUseCase evaluationManageUseCase;

	@GetMapping
	@Operation(summary = "평가 조회")
	public ResponseDto<EvaluationResponses> findEvaluations(@RequestParam String applicationId,
		@CurrentUser User user) {
		EvaluationResponses responses = evaluationManageUseCase.findEvaluations(applicationId, user);

		return ResponseDto.of(OK.value(), SUCCESS_READ_EVALUATION.getMessage(), responses);
	}

	@PatchMapping("/status")
	@Operation(summary = "합불 결과 변경")
	public ResponseDto<Void> updatedStatus(@RequestParam String applicationId,
		@RequestBody @Valid ApplicationStatusRequest request,
		@CurrentUser User user) {
		evaluationManageUseCase.updateStatus(applicationId, request, user);

		return ResponseDto.of(OK.value(), SUCCESS_UPDATE_EVALUATION.getMessage());
	}

	@PostMapping("/rating")
	@Operation(summary = "임원 평가 추가")
	public ResponseDto<Void> saveRating(@RequestParam String applicationId,
		@RequestBody @Valid EvaluationRequest request,
		@CurrentUser User user) {
		evaluationManageUseCase.saveRating(applicationId, request, user);

		return ResponseDto.of(OK.value(), SUCCESS_SAVE_EVALUATION.getMessage());
	}

	@DeleteMapping("/rating")
	@Operation(summary = "임원 평가 삭제")
	public ResponseDto<Void> deleteRating(@RequestParam Long evaluationId,
		@CurrentUser User user) {
		evaluationManageUseCase.deleteRating(evaluationId, user);

		return ResponseDto.of(OK.value(), SUCCESS_DELETE_EVALUATION.getMessage());
	}

	@PatchMapping("/rating")
	@Operation(summary = "임원 평가 변경")
	public ResponseDto<Void> updateRating(@RequestParam Long evaluationId,
		@RequestBody @Valid EvaluationRequest request,
		@CurrentUser User user) {
		evaluationManageUseCase.updateRating(evaluationId, request, user);

		return ResponseDto.of(OK.value(), SUCCESS_UPDATE_EVALUATION.getMessage());
	}

	@PostMapping("/memo")
	@Operation(summary = "평가 메모 추가")
	public ResponseDto<Void> updateRating(@RequestParam String applicationId,
		@RequestBody @Valid EvaluationMemoRequest request,
		@CurrentUser User user) {
		evaluationManageUseCase.createMemo(applicationId, request, user);

		return ResponseDto.of(OK.value(), SUCCESS_SAVE_EVALUATION.getMessage());
	}

	@DeleteMapping("/memo/{memoId}")
	@Operation(summary = "평가 메모 삭제")
	public ResponseDto<Void> deleteMemo(@PathVariable Long memoId,
		@CurrentUser User user) {
		evaluationManageUseCase.deleteMemo(memoId, user);

		return ResponseDto.of(OK.value(), SUCCESS_DELETE_EVALUATION.getMessage());
	}

	@PatchMapping("/memo/{memoId}")
	@Operation(summary = "평가 메모 수정")
	public ResponseDto<Void> updateMemo(@PathVariable Long memoId,
		@RequestBody @Valid EvaluationMemoRequest request,
		@CurrentUser User user) {
		evaluationManageUseCase.updateMemo(memoId, request, user);

		return ResponseDto.of(OK.value(), SUCCESS_UPDATE_EVALUATION.getMessage());
	}
}
