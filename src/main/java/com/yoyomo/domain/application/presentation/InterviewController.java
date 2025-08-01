package com.yoyomo.domain.application.presentation;

import static com.yoyomo.domain.application.presentation.constant.ResponseMessage.*;
import static org.springframework.http.HttpStatus.*;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yoyomo.domain.application.application.dto.request.InterviewRecordRequest;
import com.yoyomo.domain.application.application.dto.response.InterviewRecordDetailResponse;
import com.yoyomo.domain.application.application.dto.response.InterviewRecordResponse;
import com.yoyomo.domain.application.application.usecase.InterviewRecordManageUseCase;
import com.yoyomo.domain.user.domain.entity.User;
import com.yoyomo.global.common.annotation.CurrentUser;
import com.yoyomo.global.common.dto.ResponseDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "INTERVIEW_RECORD")
@RestController
@RequiredArgsConstructor
@RequestMapping("/interviews")
public class InterviewController {

	private final InterviewRecordManageUseCase interviewRecordManageUseCase;

	@PostMapping
	@Operation(summary = "면접 기록 추가")
	public ResponseDto<InterviewRecordResponse> saveInterviewRecord(@RequestParam UUID applicationId,
		@CurrentUser User user,
		@RequestBody @Valid InterviewRecordRequest request) {
		InterviewRecordResponse response = interviewRecordManageUseCase.saveInterviewRecord(applicationId, user,
			request);

		return ResponseDto.of(OK.value(), SUCCESS_SAVE_INTERVIEW_RECORD.getMessage(), response);
	}

	@GetMapping
	@Operation(summary = "면접 기록 조회")
	public ResponseDto<List<InterviewRecordDetailResponse>> findInterviewRecord(@RequestParam UUID applicationId,
		@CurrentUser User user) {
		List<InterviewRecordDetailResponse> responses = interviewRecordManageUseCase.readAll(applicationId, user);
		return ResponseDto.of(OK.value(), SUCCESS_READ_INTERVIEW_RECORD.getMessage(), responses);
	}

	@DeleteMapping("/{interviewRecordId}")
	@Operation(summary = "면접 기록 삭제")
	public ResponseDto<Void> deleteInterviewRecord(@PathVariable Long interviewRecordId,
		@CurrentUser User user) {
		interviewRecordManageUseCase.delete(interviewRecordId, user);

		return ResponseDto.of(OK.value(), SUCCESS_DELETE_INTERVIEW_RECORD.getMessage());
	}

	@PatchMapping("/{interviewRecordId}")
	@Operation(summary = "면접 기록 수정")
	public ResponseDto<Void> updateInterviewRecord(@PathVariable Long interviewRecordId,
		@CurrentUser User user,
		@RequestBody @Valid InterviewRecordRequest request) {
		interviewRecordManageUseCase.update(interviewRecordId, user, request);

		return ResponseDto.of(OK.value(), SUCCESS_UPDATE_INTERVIEW_RECORD.getMessage());
	}
}
