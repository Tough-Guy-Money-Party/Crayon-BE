package com.yoyomo.domain.recruitment.presentation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yoyomo.domain.form.application.dto.req.FormUpdateRequest;
import com.yoyomo.domain.recruitment.application.dto.req.RecruitmentModifyRequest;
import com.yoyomo.domain.recruitment.application.dto.req.RecruitmentRequest;
import com.yoyomo.domain.recruitment.application.dto.res.RecruitmentDetailsResponse;
import com.yoyomo.domain.recruitment.application.dto.res.RecruitmentResponse;
import com.yoyomo.domain.recruitment.application.usecase.RecruitmentManageUseCase;
import com.yoyomo.domain.recruitment.domain.service.RecruitmentGetService;
import com.yoyomo.global.config.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.yoyomo.domain.recruitment.presentation.constant.ResponseMessage.*;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Tag(name = "RECRUITMENT")
@RestController
@RequiredArgsConstructor
@RequestMapping("/recruitments")
public class RecruitmentController {
    private final RecruitmentManageUseCase recruitmentManageUseCase;
    private final RecruitmentGetService recruitmentGetService;

    @PostMapping
    @Operation(summary = "모집 생성")
    public ResponseDto create(@RequestBody RecruitmentRequest request, Authentication authentication) {

        String email = authentication.getName();
        String clubId = recruitmentGetService.getClubId(email);

        RecruitmentRequest updatedRequest = request.withClubId(clubId);
        recruitmentManageUseCase.create(updatedRequest);
        return ResponseDto.of(CREATED.value(), SUCCESS_CREATE.getMessage());
    }

    @GetMapping("/{recruitmentId}")
    @Operation(summary = "모집 상세 조회")
    public ResponseDto<RecruitmentDetailsResponse> read(@PathVariable String recruitmentId) {
        RecruitmentDetailsResponse response = recruitmentManageUseCase.read(recruitmentId);
        return ResponseDto.of(OK.value(), SUCCESS_READ.getMessage(), response);
    }

    @GetMapping
    @Operation(summary = "모집 목록 조회")
    public ResponseDto<List<RecruitmentResponse>> readAll(HttpServletRequest httpServletRequest, Authentication authentication) throws JsonProcessingException {

        String email = authentication.getName();
        String clubId = recruitmentGetService.getClubId(email);

        List<RecruitmentResponse> responses = recruitmentManageUseCase.readAll(clubId);
        return ResponseDto.of(OK.value(), SUCCESS_READ.getMessage(), responses);
    }

    @PatchMapping("/{recruitmentId}")
    @Operation(summary = "모집 수정", description = "지원폼을 제외한 모집 정보를 수정합니다.")
    public ResponseDto update(@PathVariable String recruitmentId, @RequestBody RecruitmentModifyRequest request) {
        recruitmentManageUseCase.update(recruitmentId, request);
        return ResponseDto.of(OK.value(), SUCCESS_UPDATE.getMessage());
    }

    @PutMapping("/{recruitmentId}/forms")
    @Operation(summary = "모집 내부 지원폼 수정", description = "모집의 지원폼을 수정합니다. [지원폼 관리]의 지원폼과는 별도로 관리됩니다.")
    public ResponseDto update(@PathVariable String recruitmentId, @RequestBody FormUpdateRequest request) {
        recruitmentManageUseCase.update(recruitmentId, request);
        return ResponseDto.of(OK.value(), SUCCESS_UPDATE.getMessage());
    }

    @DeleteMapping("/{recruitmentId}")
    @Operation(summary = "모집 삭제")
    public ResponseDto update(@PathVariable String recruitmentId) {
        recruitmentManageUseCase.delete(recruitmentId);
        return ResponseDto.of(OK.value(), SUCCESS_DELETE.getMessage());
    }
}
