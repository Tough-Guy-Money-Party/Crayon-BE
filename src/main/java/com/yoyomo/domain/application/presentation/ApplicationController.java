package com.yoyomo.domain.application.presentation;

import com.yoyomo.domain.application.application.dto.req.ApplicationRequest;
import com.yoyomo.domain.application.application.dto.req.ApplicationStatusRequest;
import com.yoyomo.domain.application.application.dto.req.AssessmentRequest;
import com.yoyomo.domain.application.application.dto.res.ApplicationDetailsResponse;
import com.yoyomo.domain.application.application.dto.res.ApplicationManageResponse;
import com.yoyomo.domain.application.application.dto.res.ApplicationResponse;
import com.yoyomo.domain.application.application.dto.res.MyApplicationsResponse;
import com.yoyomo.domain.application.application.usecase.ApplicationManageUseCase;
import com.yoyomo.domain.application.application.usecase.ApplyUseCase;
import com.yoyomo.domain.application.presentation.constant.ResponseMessage;
import com.yoyomo.domain.user.application.usecase.ApplicantInfoUseCaseImpl;
import com.yoyomo.domain.user.domain.entity.Applicant;
import com.yoyomo.global.config.dto.ResponseDto;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.yoyomo.domain.application.presentation.constant.ResponseMessage.*;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Tag(name = "APPLICATION")
@RestController
@RequiredArgsConstructor
@RequestMapping("/applications")
public class ApplicationController {
    private final ApplicantInfoUseCaseImpl applicantInfoUseCaseImpl;
    private final ApplyUseCase applyUseCase;
    private final ApplicationManageUseCase applicationManageUseCase;

    @PostMapping
    @Operation(summary = "지원서 작성")
    public ResponseDto apply(@RequestBody ApplicationRequest request) {
        applyUseCase.create(request);
        return ResponseDto.of(CREATED.value(), SUCCESS_CREATE.getMessage());
    }

    @Hidden
    @PutMapping("/{applicationId}")
    @Operation(summary = "지원서 수정")
    public ResponseDto apply(Authentication authentication,
                             @PathVariable String applicationId,
                             @RequestBody ApplicationRequest applicationRequest) {
        Applicant applicant = applicantInfoUseCaseImpl.get(authentication);
        applyUseCase.update(applicant, applicationId, applicationRequest);
        return ResponseDto.of(OK.value(), SUCCESS_UPDATE.getMessage());
    }

    @Hidden
    @GetMapping("/me/{applicationId}")
    @Operation(summary = "내 지원서 상세 조회")
    public ResponseDto<ApplicationDetailsResponse> read(Authentication authentication,
                                                        @PathVariable String applicationId) {
        Applicant applicant = applicantInfoUseCaseImpl.get(authentication);
        ApplicationDetailsResponse response = applyUseCase.read(applicant, applicationId);
        return ResponseDto.of(OK.value(), SUCCESS_READ.getMessage(), response);
    }

    @Hidden
    @GetMapping("/me")
    @Operation(summary = "내 지원서 목록 조회")
    public ResponseDto<List<MyApplicationsResponse>> readAll(Authentication authentication) {
        Applicant applicant = applicantInfoUseCaseImpl.get(authentication);
        List<MyApplicationsResponse> response = applyUseCase.readAll(applicant);
        return ResponseDto.of(OK.value(), SUCCESS_READ.getMessage(), response);
    }

    @GetMapping
    @Operation(summary = "모집 지원서 목록 조회")
    public ResponseDto<List<ApplicationResponse>> readApplications(@RequestParam String recruitmentId) {
        List<ApplicationResponse> responses = applicationManageUseCase.readAll(recruitmentId);
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_READ.getMessage(), responses);
    }

    @GetMapping("/{applicationId}")
    @Operation(summary = "모집 지원서 상세 조회")
    public ResponseDto<ApplicationManageResponse> readApplication(@PathVariable String applicationId) {
        ApplicationManageResponse response = applicationManageUseCase.read(applicationId);
        return ResponseDto.of(OK.value(), SUCCESS_READ.getMessage(), response);
    }

    @PatchMapping("/{applicationId}")
    @Operation(summary = "모집 지원 단계 수정")
    public ResponseDto<Void> update(@PathVariable String applicationId, @RequestBody ApplicationStatusRequest request) {
        applicationManageUseCase.update(applicationId, request);
        return ResponseDto.of(OK.value(), SUCCESS_UPDATE.getMessage());
    }

    @GetMapping("/search")
    @Operation(summary = "모집 지원서 검색")
    public ResponseDto<List<ApplicationResponse>> readApplicationsByApplicantName(@RequestParam String recruitmentId, @RequestParam String name) {
        List<ApplicationResponse> responses = applicationManageUseCase.readAllByApplicantName(recruitmentId, name);
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_READ.getMessage(), responses);
    }

    @PatchMapping("/assessment/{applicationId}")
    @Operation(summary = "평가 추가")
    public ResponseDto<Void> addAssessment(@PathVariable String applicationId, @RequestBody AssessmentRequest request) {
        applicationManageUseCase.addAssessment(applicationId, request);
        return ResponseDto.of(OK.value(), SUCCESS_UPDATE.getMessage());
    }
}
