package com.yoyomo.domain.recruitment.presentation;

import com.yoyomo.domain.recruitment.application.dto.response.ProcessResponseDTO;
import com.yoyomo.domain.recruitment.application.dto.response.RecruitmentResponseDTO.Response;
import com.yoyomo.domain.recruitment.application.usecase.ProcessManageUseCase;
import com.yoyomo.domain.recruitment.application.usecase.RecruitmentManageUseCase;
import com.yoyomo.domain.recruitment.domain.entity.enums.ProcessStep;
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
import java.util.UUID;

import static com.yoyomo.domain.recruitment.application.dto.request.RecruitmentRequestDTO.Save;
import static com.yoyomo.domain.recruitment.application.dto.request.RecruitmentRequestDTO.Update;
import static com.yoyomo.domain.recruitment.application.dto.response.RecruitmentResponseDTO.DetailResponse;
import static com.yoyomo.domain.recruitment.presentation.constant.ResponseMessage.*;
import static org.springframework.http.HttpStatus.OK;

@Tag(name = "RECRUITMENT")
@RestController
@RequiredArgsConstructor
@RequestMapping("/recruitments")
public class RecruitmentController {

    private final RecruitmentManageUseCase recruitmentManageUseCase;
    private final ProcessManageUseCase processManageUseCase;

    @PostMapping
    @Operation(summary = "모집 생성")
    public ResponseDto<Void> save(@RequestBody @Valid Save dto, @CurrentUser @Parameter(hidden = true) Long userId) {
        recruitmentManageUseCase.save(dto, userId);

        return ResponseDto.of(OK.value(), SUCCESS_SAVE.getMessage());
    }

    @PatchMapping("/{recruitmentId}/{formId}")
    @Operation(summary = "모집 활성화 (Recruitment - Form 매핑)")
    public ResponseDto<Void> activate(@PathVariable String recruitmentId, @PathVariable String formId,
                                      @CurrentUser @Parameter(hidden = true) Long userId) {
        recruitmentManageUseCase.activate(recruitmentId, formId, userId);

        return ResponseDto.of(OK.value(), SUCCESS_ACTIVATE.getMessage());
    }

    @PatchMapping("/{recruitmentId}/process/{processId}")
    @Operation(summary = "프로세스 스텝 이동(1-2-3)")
    public ResponseDto<Void> process(@PathVariable UUID recruitmentId, @PathVariable Long processId, @RequestParam ProcessStep step,
                                     @CurrentUser @Parameter(hidden = true) Long userId) {
        processManageUseCase.updateStep(recruitmentId, processId, step, userId);
        return ResponseDto.of(OK.value(), SUCCESS_MOVE_PROCESS_STEP.getMessage());
    }

    @GetMapping("/{recruitmentId}")
    @Operation(summary = "모집 상세 조회")
    public ResponseDto<DetailResponse> read(@PathVariable UUID recruitmentId,
                                            @CurrentUser @Parameter(hidden = true) Long userId) {
        DetailResponse response = recruitmentManageUseCase.read(recruitmentId, userId);

        return ResponseDto.of(OK.value(), SUCCESS_READ.getMessage(), response);
    }

    @GetMapping("all/{clubId}")
    @Operation(summary = "모집 목록 조회")
    public ResponseDto<Page<Response>> readAll(@RequestParam(defaultValue = "0") Integer page,
                                               @RequestParam(defaultValue = "7") Integer size,
                                               @PathVariable String clubId) {
        Page<Response> responses = recruitmentManageUseCase.readAll(PageRequest.of(page, size), clubId);

        return ResponseDto.of(OK.value(), SUCCESS_READ.getMessage(), responses);
    }

    @PatchMapping("/{recruitmentId}")
    @Operation(summary = "모집 수정")
    public ResponseDto<Void> update(@PathVariable String recruitmentId, @RequestBody @Valid Update dto,
                                    @CurrentUser @Parameter(hidden = true) Long userId) {
        recruitmentManageUseCase.update(recruitmentId, dto, userId);

        return ResponseDto.of(OK.value(), SUCCESS_UPDATE.getMessage());
    }

    @DeleteMapping("/{recruitmentId}")
    @Operation(summary = "모집 마감")
    public ResponseDto<Void> close(@PathVariable String recruitmentId,
                                   @CurrentUser @Parameter(hidden = true) Long userId) {
        recruitmentManageUseCase.close(recruitmentId, userId);

        return ResponseDto.of(OK.value(), SUCCESS_DELETE.getMessage());
    }

    @DeleteMapping("/{recruitmentId}/force")
    @Operation(summary = "모집 취소")
    public ResponseDto<Void> cancel(@PathVariable String recruitmentId,
                                    @CurrentUser @Parameter(hidden = true) Long userId) {
        recruitmentManageUseCase.cancel(recruitmentId, userId);

        return ResponseDto.of(OK.value(), SUCCESS_CANCEL.getMessage());
    }

    @GetMapping("/processes/{recruitmentId}")
    @Operation(summary = "모집 프로세스 목록 조회")
    public ResponseDto<List<ProcessResponseDTO.Response>> readAll(@PathVariable UUID recruitmentId,
                                                                  @CurrentUser @Parameter(hidden = true) Long userId) {
        List<ProcessResponseDTO.Response> responses = processManageUseCase.readAll(recruitmentId, userId);

        return ResponseDto.of(OK.value(), SUCCESS_READ_PROCESSES.getMessage(), responses);
    }
}
