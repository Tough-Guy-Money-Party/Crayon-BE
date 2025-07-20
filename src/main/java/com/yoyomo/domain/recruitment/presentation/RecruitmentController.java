package com.yoyomo.domain.recruitment.presentation;

import static com.yoyomo.domain.recruitment.presentation.constant.ResponseMessage.*;
import static org.springframework.http.HttpStatus.*;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yoyomo.domain.recruitment.application.dto.request.RecruitmentSaveRequest;
import com.yoyomo.domain.recruitment.application.dto.request.RecruitmentUpdateRequest;
import com.yoyomo.domain.recruitment.application.dto.response.ProcessResponse;
import com.yoyomo.domain.recruitment.application.dto.response.RecruitmentDetailsResponse;
import com.yoyomo.domain.recruitment.application.dto.response.RecruitmentResponseDTO.Response;
import com.yoyomo.domain.recruitment.application.usecase.ProcessManageUseCase;
import com.yoyomo.domain.recruitment.application.usecase.RecruitmentManageUseCase;
import com.yoyomo.domain.recruitment.domain.dto.RecruitmentCreateResponse;
import com.yoyomo.domain.recruitment.domain.entity.enums.ProcessStep;
import com.yoyomo.domain.user.domain.entity.User;
import com.yoyomo.global.common.annotation.CurrentUser;
import com.yoyomo.global.common.dto.ResponseDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "RECRUITMENT")
@RestController
@RequiredArgsConstructor
@RequestMapping("/recruitments")
public class RecruitmentController {

	private final RecruitmentManageUseCase recruitmentManageUseCase;
	private final ProcessManageUseCase processManageUseCase;

	@PostMapping
	@Operation(summary = "모집 생성")
	public ResponseDto<RecruitmentCreateResponse> save(
		@RequestBody @Valid RecruitmentSaveRequest request, @CurrentUser User user
	) {
		RecruitmentCreateResponse response = recruitmentManageUseCase.save(request, user);

		return ResponseDto.of(OK.value(), SUCCESS_SAVE.getMessage(), response);
	}

	@PatchMapping("/{recruitmentId}/{formId}")
	@Operation(summary = "모집 활성화 (Recruitment - Form 매핑)")
	public ResponseDto<Void> activate(@PathVariable String recruitmentId, @PathVariable String formId,
		@CurrentUser User user) {
		recruitmentManageUseCase.activate(recruitmentId, formId, user);

		return ResponseDto.of(OK.value(), SUCCESS_ACTIVATE.getMessage());
	}

	@PatchMapping("/{recruitmentId}/process/{processId}")
	@Operation(summary = "프로세스 스텝 이동(1-2-3)")
	public ResponseDto<Void> process(@PathVariable UUID recruitmentId, @PathVariable Long processId,
		@RequestParam ProcessStep step,
		@CurrentUser User user) {
		processManageUseCase.updateStep(recruitmentId, processId, step, user);
		return ResponseDto.of(OK.value(), SUCCESS_MOVE_PROCESS_STEP.getMessage());
	}

	@GetMapping("/{recruitmentId}")
	@Operation(summary = "모집 상세 조회")
	public ResponseDto<RecruitmentDetailsResponse> read(@PathVariable UUID recruitmentId) {
		RecruitmentDetailsResponse response = recruitmentManageUseCase.read(recruitmentId);

		return ResponseDto.of(OK.value(), SUCCESS_READ.getMessage(), response);
	}

	@GetMapping("/all/{clubId}")
	@Operation(summary = "모집 목록 조회")
	public ResponseDto<Page<Response>> readAll(@RequestParam(defaultValue = "0") Integer page,
		@RequestParam(defaultValue = "7") Integer size,
		@PathVariable String clubId) {
		Page<Response> responses = recruitmentManageUseCase.readAll(PageRequest.of(page, size), clubId);

		return ResponseDto.of(OK.value(), SUCCESS_READ.getMessage(), responses);
	}

	@PutMapping("/{recruitmentId}")
	@Operation(summary = "모집 수정")
	public ResponseDto<Void> update(@PathVariable String recruitmentId,
		@RequestBody @Valid RecruitmentUpdateRequest request,
		@CurrentUser User user) {
		recruitmentManageUseCase.update(recruitmentId, request, user);

		return ResponseDto.of(OK.value(), SUCCESS_UPDATE.getMessage());
	}

	@DeleteMapping("/{recruitmentId}")
	@Operation(summary = "모집 마감")
	public ResponseDto<Void> close(@PathVariable String recruitmentId,
		@CurrentUser User user) {
		recruitmentManageUseCase.close(recruitmentId, user);

		return ResponseDto.of(OK.value(), SUCCESS_DELETE.getMessage());
	}

	@DeleteMapping("/{recruitmentId}/force")
	@Operation(summary = "모집 삭제")
	public ResponseDto<Void> cancel(@PathVariable String recruitmentId,
		@CurrentUser User user) {
		recruitmentManageUseCase.cancel(recruitmentId, user);

		return ResponseDto.of(OK.value(), SUCCESS_CANCEL.getMessage());
	}

	@GetMapping("/processes/{recruitmentId}")
	@Operation(summary = "모집 프로세스 목록 조회")
	public ResponseDto<List<ProcessResponse>> readAll(@PathVariable UUID recruitmentId,
		@CurrentUser User user) {
		List<ProcessResponse> responses = processManageUseCase.readAll(recruitmentId, user);

		return ResponseDto.of(OK.value(), SUCCESS_READ_PROCESSES.getMessage(), responses);
	}

	@PostMapping("/replication/{recruitmentId}")
	@Operation(summary = "모집 복제")
	public ResponseDto<Void> replicate(@PathVariable String recruitmentId,
		@CurrentUser User user) {
		recruitmentManageUseCase.replicate(recruitmentId, user);
		return ResponseDto.of(OK.value(), SUCCESS_SAVE.getMessage());
	}
}
