package com.yoyomo.domain.application.presentation;

import static com.yoyomo.domain.application.application.dto.request.ApplicationRequestDTO.Save;
import static com.yoyomo.domain.application.application.dto.request.ApplicationVerificationRequestDto.VerificationRequest;
import static com.yoyomo.domain.application.application.dto.response.ApplicationResponseDTO.Detail;
import static com.yoyomo.domain.application.application.dto.response.ApplicationResponseDTO.Response;
import static com.yoyomo.domain.application.presentation.constant.ResponseMessage.SUCCESS_GENERATE_CODE;
import static com.yoyomo.domain.application.presentation.constant.ResponseMessage.SUCCESS_READ;
import static com.yoyomo.domain.application.presentation.constant.ResponseMessage.SUCCESS_READ_ALL;
import static com.yoyomo.domain.application.presentation.constant.ResponseMessage.SUCCESS_SAVE;
import static com.yoyomo.domain.application.presentation.constant.ResponseMessage.SUCCESS_SAVE_INTERVIEW;
import static com.yoyomo.domain.application.presentation.constant.ResponseMessage.SUCCESS_SEARCH;
import static com.yoyomo.domain.application.presentation.constant.ResponseMessage.SUCCESS_UPDATE;
import static com.yoyomo.domain.application.presentation.constant.ResponseMessage.SUCCESS_VERIFY_CODE;
import static org.springframework.http.HttpStatus.OK;

import com.yoyomo.domain.application.application.dto.request.ApplicationRequestDTO.Stage;
import com.yoyomo.domain.application.application.dto.request.ApplicationRequestDTO.Update;
import com.yoyomo.domain.application.application.dto.request.InterviewRequestDTO;
import com.yoyomo.domain.application.application.dto.response.ApplicationResponseDTO.MyResponse;
import com.yoyomo.domain.application.application.usecase.ApplicationManageUseCase;
import com.yoyomo.domain.application.application.usecase.ApplicationVerifyUseCase;
import com.yoyomo.domain.application.application.usecase.ApplyUseCase;
import com.yoyomo.domain.application.application.usecase.InterviewManageUseCase;
import com.yoyomo.global.common.annotation.CurrentUser;
import com.yoyomo.global.common.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
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

@Tag(name = "APPLICATION")
@RestController
@RequiredArgsConstructor
@RequestMapping("/applications")
public class ApplicationController {

    private final ApplyUseCase applyUseCase;
    private final InterviewManageUseCase interviewManageUseCase;
    private final ApplicationManageUseCase applicationManageUseCase;
    private final ApplicationVerifyUseCase applicationVerifyUseCase;

    // Applicant
    @PostMapping("/{recruitmentId}")
    @Operation(summary = "[Applicant] 지원서 작성")
    public ResponseDto<Void> apply(@Valid @RequestBody Save dto, @PathVariable String recruitmentId,
                                   @CurrentUser @Parameter(hidden = true) Long userId) {
        applyUseCase.apply(dto, recruitmentId, userId);

        return ResponseDto.of(OK.value(), SUCCESS_SAVE.getMessage());
    }

    @GetMapping
    @Operation(summary = "[Applicant] 내 지원서 목록 조회")
    public ResponseDto<List<Response>> readAll(@CurrentUser @Parameter(hidden = true) Long userId) {
        List<Response> responses = applyUseCase.readAll(userId);

        return ResponseDto.of(OK.value(), SUCCESS_READ_ALL.getMessage(), responses);
    }

    @GetMapping("/{applicationId}")
    @Operation(summary = "[Applicant] 내가 쓴 지원서 불러오기")
    public ResponseDto<MyResponse> readApplication(@PathVariable String applicationId,
                                                   @CurrentUser @Parameter(hidden = true) Long userId) {
        MyResponse response = applyUseCase.read(applicationId, userId);

        return ResponseDto.of(OK.value(), SUCCESS_READ.getMessage(), response);
    }

    @PatchMapping("/{applicationId}")
    @Operation(summary = "[Applicant] 내 지원서 응답 수정")
    public ResponseDto<Void> update(@PathVariable String applicationId, @RequestBody @Valid Update dto,
                                    @CurrentUser @Parameter(hidden = true) Long userId) {
        applyUseCase.update(applicationId, dto, userId);

        return ResponseDto.of(OK.value(), SUCCESS_UPDATE.getMessage());
    }

    @DeleteMapping("/{applicationId}")
    @Operation(summary = "[Applicant] 지원서 삭제")
    public ResponseDto<Void> delete(@PathVariable String applicationId) {
        applyUseCase.delete(applicationId);

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

    @GetMapping("/manager/{recruitmentId}/all")
    @Operation(summary = "[Manager] 지원서 목록 조회") // todo 로그인 후 Request Body 수정
    public ResponseDto<Page<Detail>> readAll(@PathVariable String recruitmentId,
                                             @CurrentUser @Parameter(hidden = true) Long userId,
                                             @RequestParam Integer stage, @RequestParam Integer page,
                                             @RequestParam Integer size) {
        Page<Detail> response = applicationManageUseCase.readAll(recruitmentId, stage, userId,
                PageRequest.of(page, size));

        return ResponseDto.of(OK.value(), SUCCESS_READ_ALL.getMessage(), response);
    }

    @GetMapping("/manager/{applicationId}") // 수정: URL /manager 대신 다른 방법 찾기 (manager_id 라던가..)
    @Operation(summary = "[Manager] 지원서 상세 조회")
    public ResponseDto<Detail> read(@PathVariable String applicationId,
                                    @CurrentUser @Parameter(hidden = true) Long userId) {
        Detail response = applicationManageUseCase.read(applicationId, userId);

        return ResponseDto.of(OK.value(), SUCCESS_READ.getMessage(), response);
    }

    @GetMapping("/manager/{recruitmentId}/search")
    @Operation(summary = "[Manager] 이름으로 지원서 검색")
    public ResponseDto<Page<Response>> search(@RequestParam String name, @PathVariable String recruitmentId,
                                              @CurrentUser @Parameter(hidden = true) Long userId,
                                              @RequestParam Integer page, @RequestParam Integer size) {
        Page<Response> responses = applicationManageUseCase.search(name, recruitmentId, userId,
                PageRequest.of(page, size));

        return ResponseDto.of(OK.value(), SUCCESS_SEARCH.getMessage(), responses);
    }

    @PatchMapping("/manager/{recruitmentId}")
    @Operation(summary = "[Manager] 지원서 단계 수정 (다중/단일)")
    public ResponseDto<Void> update(@RequestBody @Valid Stage dto, @CurrentUser @Parameter(hidden = true) Long userId,
                                    @PathVariable String recruitmentId) {
        applicationManageUseCase.updateProcess(dto, userId, recruitmentId);

        return ResponseDto.of(OK.value(), SUCCESS_UPDATE.getMessage());
    }

    @PatchMapping("/{applicationId}/interview")
    @Operation(summary = "[Manager] 면접 일정 설정")
    public ResponseDto<Void> saveInterview(@PathVariable String applicationId,
                                           @RequestBody InterviewRequestDTO.Save dto,
                                           @CurrentUser @Parameter(hidden = true) Long userId) {
        interviewManageUseCase.saveInterview(applicationId, dto, userId);

        return ResponseDto.of(OK.value(), SUCCESS_SAVE_INTERVIEW.getMessage());
    }
}
