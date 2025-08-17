package com.yoyomo.domain.application.presentation;

import static com.yoyomo.domain.application.application.dto.request.ApplicationVerificationRequest.*;
import static com.yoyomo.domain.application.application.dto.response.ApplicationResponse.*;
import static com.yoyomo.domain.application.presentation.constant.ResponseMessage.*;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yoyomo.domain.application.application.dto.request.ApplicationImportRequest;
import com.yoyomo.domain.application.application.dto.request.ApplicationMoveRequest;
import com.yoyomo.domain.application.application.dto.request.ApplicationSaveRequest;
import com.yoyomo.domain.application.application.dto.request.ApplicationUpdateRequest;
import com.yoyomo.domain.application.application.dto.request.InterviewRequest;
import com.yoyomo.domain.application.application.dto.request.StageUpdateRequest;
import com.yoyomo.domain.application.application.dto.response.ApplicantsResponse;
import com.yoyomo.domain.application.application.dto.response.ApplicationDetailResponse;
import com.yoyomo.domain.application.application.dto.response.ApplicationListResponse;
import com.yoyomo.domain.application.application.dto.response.MyApplicationResponse;
import com.yoyomo.domain.application.application.usecase.ApplicationImportUseCase;
import com.yoyomo.domain.application.application.usecase.ApplicationManageUseCase;
import com.yoyomo.domain.application.application.usecase.ApplicationVerifyUseCase;
import com.yoyomo.domain.application.application.usecase.ApplyUseCase;
import com.yoyomo.domain.application.application.usecase.InterviewManageUseCase;
import com.yoyomo.domain.application.application.usecase.dto.ApplicationCondition;
import com.yoyomo.domain.user.domain.entity.User;
import com.yoyomo.global.common.annotation.CurrentUser;
import com.yoyomo.global.common.dto.ResponseDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "APPLICATION")
@RestController
@RequiredArgsConstructor
@RequestMapping("/applications")
public class ApplicationController {

	private final ApplyUseCase applyUseCase;
	private final InterviewManageUseCase interviewManageUseCase;
	private final ApplicationManageUseCase applicationManageUseCase;
	private final ApplicationVerifyUseCase applicationVerifyUseCase;
	private final ApplicationImportUseCase applicationImportUseCase;

	// Applicant
	@PostMapping("/{recruitmentId}")
	@Operation(summary = "[Applicant] 지원서 작성")
	public ResponseDto<Void> apply(
		@Valid @RequestBody ApplicationSaveRequest dto,
		@PathVariable UUID recruitmentId,
		@CurrentUser User user
	) {
		applyUseCase.apply(dto, recruitmentId, user);

		return ResponseDto.of(OK.value(), SUCCESS_SAVE.getMessage());
	}

	@GetMapping
	@Operation(summary = "[Applicant] 내 지원서 목록 조회")
	public ResponseDto<List<Response>> readAll(@CurrentUser User user) {
		List<Response> responses = applyUseCase.readAll(user);

		return ResponseDto.of(OK.value(), SUCCESS_READ_ALL.getMessage(), responses);
	}

	@GetMapping("/{applicationId}")
	@Operation(summary = "[Applicant] 내가 쓴 지원서 불러오기")
	public ResponseDto<MyApplicationResponse> readApplication(
		@PathVariable String applicationId,
		@CurrentUser User user
	) {
		MyApplicationResponse response = applyUseCase.read(applicationId, user);

		return ResponseDto.of(OK.value(), SUCCESS_READ.getMessage(), response);
	}

	@PatchMapping("/{applicationId}")
	@Operation(summary = "[Applicant] 내 지원서 응답 수정")
	public ResponseDto<Void> update(@PathVariable String applicationId,
		@RequestBody @Valid ApplicationUpdateRequest dto,
		@CurrentUser User user
	) {
		applyUseCase.update(applicationId, dto, user);

		return ResponseDto.of(OK.value(), SUCCESS_UPDATE.getMessage());
	}

	@DeleteMapping("/{applicationId}")
	@Operation(summary = "[Applicant] 지원서 삭제")
	public ResponseDto<Void> delete(@PathVariable String applicationId,
		@CurrentUser User user) {
		applyUseCase.delete(applicationId, user);

		return ResponseDto.of(OK.value(), SUCCESS_UPDATE.getMessage());
	}

	@GetMapping("/mail/{email}/")
	@Operation(summary = "[Applicant] 지원서 작성 시 이메일 인증 코드 요청")
	public ResponseDto<String> readCode(@PathVariable String email) {
		applicationVerifyUseCase.generate(email);

		return ResponseDto.of(OK.value(), SUCCESS_GENERATE_CODE.getMessage());
	}

	@PostMapping("/mail")
	@Operation(summary = "[Applicant] 이메일 인증 요청. 가능시간 5분")
	public ResponseDto<Void> verify(@Valid @RequestBody VerificationRequest dto) {
		applicationVerifyUseCase.verify(dto);

		return ResponseDto.of(OK.value(), SUCCESS_VERIFY_CODE.getMessage());
	}

	@PostMapping("/{recruitmentId}/import")
	@Operation(summary = "[Manager] 외부 지원서 응답 가져오기")
	public ResponseDto<Void> importApplications(
		@RequestBody ApplicationImportRequest request,
		@PathVariable UUID recruitmentId,
		@CurrentUser User user
	) {
		applicationImportUseCase.importApplications(recruitmentId, user, request);

		return ResponseDto.of(OK.value(), SUCCESS_SAVE.getMessage());
	}

	@GetMapping("/manager")
	@Operation(summary = "[Manager] 지원서 목록 조회")
	public ResponseDto<Page<ApplicationListResponse>> readAll(
		@CurrentUser User user,
		@RequestParam UUID recruitmentId,
		@RequestParam Integer stage,
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "5") int size,
		@RequestParam(defaultValue = "latest") String sort,
		@RequestParam(defaultValue = "all") String evaluationFilter,
		@RequestParam(defaultValue = "all") String resultFilter
	) {
		ApplicationCondition condition = ApplicationCondition.from(sort, evaluationFilter, resultFilter);
		Page<ApplicationListResponse> response = applicationManageUseCase.readAll(
			recruitmentId, stage, user, condition, PageRequest.of(page, size)
		);

		return ResponseDto.of(OK.value(), SUCCESS_READ_ALL.getMessage(), response);
	}

	@GetMapping("/manager/{processId}/applicants")
	@Operation(summary = "[Manager] 지원자 목록 조회")
	public ResponseDto<List<ApplicantsResponse>> readAllApplicants(
		@PathVariable Long processId, @CurrentUser User user
	) {
		List<ApplicantsResponse> responses = applicationManageUseCase.readAll(processId, user);
		return ResponseDto.of(OK.value(), SUCCESS_READ_ALL.getMessage(), responses);
	}

	@GetMapping("/manager/{applicationId}") // 수정: URL /manager 대신 다른 방법 찾기 (manager_id 라던가..)
	@Operation(summary = "[Manager] 지원서 상세 조회")
	public ResponseDto<ApplicationDetailResponse> read(
		@PathVariable String applicationId,
		@CurrentUser User user
	) {
		ApplicationDetailResponse response = applicationManageUseCase.read(applicationId, user);

		return ResponseDto.of(OK.value(), SUCCESS_READ.getMessage(), response);
	}

	@GetMapping("/manager/search")
	@Operation(summary = "[Manager] 이름으로 지원서 검색")
	public ResponseDto<Page<ApplicationListResponse>> search(
		@CurrentUser User user,
		@RequestParam UUID recruitmentId,
		@RequestParam Integer stage,
		@RequestParam String name,
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "5") int size
	) {
		Page<ApplicationListResponse> responses = applicationManageUseCase.search(
			name, recruitmentId, stage, user, PageRequest.of(page, size)
		);

		return ResponseDto.of(OK.value(), SUCCESS_SEARCH.getMessage(), responses);
	}

	@PatchMapping("/manager/{recruitmentId}")
	@Operation(summary = "[Manager] 지원서 단계 수정 (다중/단일)")
	public ResponseDto<Void> update(
		@RequestBody @Valid StageUpdateRequest dto,
		@CurrentUser User user,
		@PathVariable UUID recruitmentId
	) {
		applicationManageUseCase.updateProcess(dto, user, recruitmentId);

		return ResponseDto.of(OK.value(), SUCCESS_UPDATE.getMessage());
	}

	@PatchMapping("/manager/{recruitmentId}/process")
	@Operation(summary = "[Manager] 합격자 이동")
	public ResponseDto<Void> moveApplicant(
		@PathVariable UUID recruitmentId,
		@RequestBody @Valid ApplicationMoveRequest dto,
		@CurrentUser User user
	) {
		applicationManageUseCase.moveApplicant(recruitmentId, dto, user);
		return ResponseDto.of(OK.value(), SUCCESS_MOVE_PASS.getMessage());
	}

	@PatchMapping("/{applicationId}/interview")
	@Operation(summary = "[Manager] 면접 일정 설정")
	public ResponseDto<Void> saveInterview(
		@PathVariable String applicationId,
		@RequestBody InterviewRequest.Save dto,
		@CurrentUser User user
	) {
		interviewManageUseCase.saveInterview(applicationId, dto, user);

		return ResponseDto.of(OK.value(), SUCCESS_SAVE_INTERVIEW.getMessage());
	}
}
