package com.yoyomo.domain.recruitment.presentation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.club.domain.service.ClubGetService;
import com.yoyomo.domain.form.application.dto.req.FormUpdateRequest;
import com.yoyomo.domain.recruitment.application.dto.req.RecruitmentRequest;
import com.yoyomo.domain.recruitment.application.dto.res.RecruitmentDetailsResponse;
import com.yoyomo.domain.recruitment.application.dto.res.RecruitmentResponse;
import com.yoyomo.domain.recruitment.application.usecase.RecruitmentManageUseCase;
import com.yoyomo.domain.user.domain.entity.Manager;
import com.yoyomo.domain.user.domain.service.UserGetService;
import com.yoyomo.global.config.dto.ResponseDto;
import com.yoyomo.global.config.jwt.JwtProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
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
    private final JwtProvider jwtProvider;
    private final UserGetService userGetService;
    private final ClubGetService clubGetService;


    @PostMapping
    @Operation(summary = "모집 생성")
    public ResponseDto create(@RequestBody RecruitmentRequest request) {
        recruitmentManageUseCase.create(request);
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
    public ResponseDto<List<RecruitmentResponse>> readAll(HttpServletRequest request) throws JsonProcessingException {

        String token = jwtProvider.extractTokenFromRequest(request);

        String email = jwtProvider.getEmail(token);
        Manager manager = userGetService.findByEmail(email);

        List<Club> clubs = manager.getClubs();
        List<String> clubIds = clubGetService.extractClubIds(clubs);
        System.out.println("clubIds = " + clubIds);
        // 일단 첫번쨰 클럽만 넘겨 주기 - MVP
        String clubId = clubIds.get(0);

        List<RecruitmentResponse> responses = recruitmentManageUseCase.readAll(clubId);
        return ResponseDto.of(OK.value(), SUCCESS_READ.getMessage(), responses);
    }

    @PatchMapping("/{recruitmentId}")
    @Operation(summary = "모집 수정", description = "지원폼을 제외한 모집 정보를 수정합니다.")
    public ResponseDto update(@PathVariable String recruitmentId, @RequestBody RecruitmentRequest request) {
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
