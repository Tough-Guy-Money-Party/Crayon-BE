package com.yoyomo.domain.application.presentation;

import com.yoyomo.domain.application.application.dto.request.ApplicationRequestDTO.Stage;
import com.yoyomo.domain.application.application.dto.request.ApplicationRequestDTO.Update;
import com.yoyomo.domain.application.application.dto.response.ApplicationResponseDTO.MyResponse;
import com.yoyomo.domain.application.application.usecase.ApplicationManageUseCase;
import com.yoyomo.domain.application.application.usecase.ApplyUseCase;
import com.yoyomo.domain.user.application.dto.request.UserRequestDTO.Find;
import com.yoyomo.global.common.annotation.CurrentUser;
import com.yoyomo.global.common.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.yoyomo.domain.application.application.dto.request.ApplicationRequestDTO.Save;
import static com.yoyomo.domain.application.application.dto.response.ApplicationResponseDTO.Detail;
import static com.yoyomo.domain.application.application.dto.response.ApplicationResponseDTO.Response;
import static com.yoyomo.domain.application.presentation.constant.ResponseMessage.*;
import static org.springframework.http.HttpStatus.OK;

@Tag(name = "APPLICATION")
@RestController
@RequiredArgsConstructor
@RequestMapping("/applications")
public class ApplicationController {

    private final ApplyUseCase applyUseCase;
    private final ApplicationManageUseCase applicationManageUseCase;

    // Applicant
    @PostMapping("/{recruitmentId}")
    @Operation(summary = "[Applicant] 지원서 작성")
    public ResponseDto<Void> apply(@RequestBody @Valid Save dto, @PathVariable String recruitmentId) {
        applyUseCase.apply(dto, recruitmentId);
        return ResponseDto.of(OK.value(), SUCCESS_SAVE.getMessage());
    }

    @GetMapping
    @Operation(summary = "[Applicant] 내 지원서 목록 조회")
    public ResponseDto<List<Response>> readAll(@RequestBody @Valid Find dto) {
        return ResponseDto.of(OK.value(), SUCCESS_READ_ALL.getMessage(), applyUseCase.readAll(dto));
    }

    @GetMapping("/{applicationId}")
    @Operation(summary = "[Applicant] 내가 쓴 지원서 불러오기")
    public ResponseDto<MyResponse> read(@PathVariable String applicationId) {
        return ResponseDto.of(OK.value(), SUCCESS_READ.getMessage(), applyUseCase.read(applicationId));
    }

    @PatchMapping("/{applicationId}")
    @Operation(summary = "[Applicant] 내 지원서 응답 수정")
    public ResponseDto<Void> update(@PathVariable String applicationId, @RequestBody @Valid Update dto) {
        applyUseCase.update(applicationId, dto);
        return ResponseDto.of(OK.value(), SUCCESS_UPDATE.getMessage());
    }

    @DeleteMapping("/{applicationId}")
    @Operation(summary = "[Applicant] 지원서 삭제")
    public ResponseDto<Void> delete(@PathVariable String applicationId) {
        applyUseCase.delete(applicationId);
        return ResponseDto.of(OK.value(), SUCCESS_UPDATE.getMessage());
    }


    // Manager
    @GetMapping("/manager/{recruitmentId}/all")
    @Operation(summary = "[Manager] 지원서 목록 조회")
    public ResponseDto<Page<Detail>> readAll(@PathVariable String recruitmentId, @CurrentUser Long userId, @RequestParam Integer stage, @RequestParam Integer page, @RequestParam Integer size) {
        return ResponseDto.of(OK.value(), SUCCESS_READ_ALL.getMessage(), applicationManageUseCase.readAll(recruitmentId, stage, userId, PageRequest.of(page, size)));
    }

    @GetMapping("/manager/{applicationId}") // 수정: URL /manager 대신 다른 방법 찾기 (manager_id 라던가..)
    @Operation(summary = "[Manager] 지원서 상세 조회")
    public ResponseDto<Detail> read(@PathVariable String applicationId, @CurrentUser Long userId) {
        return ResponseDto.of(OK.value(), SUCCESS_READ.getMessage(), applicationManageUseCase.read(applicationId, userId));
    }

    @GetMapping("/manager/{recruitmentId}/search")
    @Operation(summary = "[Manager] 이름으로 지원서 검색")
    public ResponseDto<Page<Response>> search(@RequestParam String name, @PathVariable String recruitmentId, @CurrentUser @Parameter(hidden = true) Long userId, @RequestParam Integer page, @RequestParam Integer size) {
        return ResponseDto.of(OK.value(), SUCCESS_SEARCH.getMessage(), applicationManageUseCase.search(name, recruitmentId, userId, PageRequest.of(page, size)));
    }

    @PatchMapping("/manager/{recruitmentId}")
    @Operation(summary = "[Manager] 지원서 단계 수정 (다중/단일)")
    public ResponseDto<Void> update(@RequestBody @Valid Stage dto, @CurrentUser Long userId, @PathVariable String recruitmentId) {
        applicationManageUseCase.update(dto, userId, recruitmentId);
        return ResponseDto.of(OK.value(), SUCCESS_UPDATE.getMessage());
    }
}
