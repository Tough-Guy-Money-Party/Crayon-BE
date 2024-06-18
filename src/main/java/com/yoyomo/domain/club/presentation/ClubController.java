package com.yoyomo.domain.club.presentation;

import com.yoyomo.domain.club.application.dto.req.ClubRequest;
import com.yoyomo.domain.club.application.dto.req.ParticipationRequest;
import com.yoyomo.domain.club.application.dto.req.RemoveManagerRequest;
import com.yoyomo.domain.club.application.dto.res.ClubCreateResponse;
import com.yoyomo.domain.club.application.dto.res.ClubManagerResponse;
import com.yoyomo.domain.club.application.dto.res.ClubResponse;
import com.yoyomo.domain.club.application.dto.res.ParticipationResponse;
import com.yoyomo.domain.club.application.usecase.ClubManageUseCase;

import com.yoyomo.domain.user.application.dto.res.ManagerResponse;
import com.yoyomo.global.config.participation.dto.ParticipationCodeResponse;
import com.yoyomo.global.config.dto.ResponseDto;
import com.yoyomo.global.config.participation.service.ParticipationCodeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.yoyomo.domain.club.presentation.constant.ResponseMessage.*;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;


@Tag(name = "CLUB")
@RestController
@RequiredArgsConstructor
@RequestMapping("/clubs")
public class ClubController {
    private final ClubManageUseCase clubManageUseCase;
    private final ParticipationCodeService participationCodeService;


    @PostMapping
    @Operation(summary = "동아리 생성")
    public ResponseDto<ClubCreateResponse> create(@RequestBody ClubRequest clubRequest, Authentication authentication) {
        ClubCreateResponse response = clubManageUseCase.create(clubRequest, authentication.getName());
        return ResponseDto.of(CREATED.value(), SUCCESS_CREATE.getMessage(), response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "동아리 조회")
    public ResponseDto<ClubResponse> update(@PathVariable String id) {
        ClubResponse response = clubManageUseCase.read(id);
        return ResponseDto.of(OK.value(), SUCCESS_READ.getMessage(), response);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "동아리 수정")
    public ResponseDto update(@PathVariable String id, @RequestBody ClubRequest clubRequest) {
        clubManageUseCase.update(id, clubRequest);
        return ResponseDto.of(OK.value(), SUCCESS_UPDATE.getMessage());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "동아리 삭제")
    public ResponseDto delete(@PathVariable String id) {
        clubManageUseCase.delete(id);
        return ResponseDto.of(OK.value(), SUCCESS_DELETE.getMessage());
    }


    @GetMapping("/participation/{clubId}")
    @Operation(summary = "동아리 관리자 조회")
    public ResponseDto<List<ClubManagerResponse>> getManagers(@PathVariable String clubId) {
        List<ClubManagerResponse> response = clubManageUseCase.getManagers(clubId);
        return ResponseDto.of(OK.value(), SUCCESS_GET_MANAGERS.getMessage(), response);
    }


    @PatchMapping("/participation")
    @Operation(summary = "동아리 관리자 추가")
    public ResponseDto<ParticipationResponse> participation(@RequestBody ParticipationRequest participationRequest, Authentication authentication) {
        ParticipationResponse response = clubManageUseCase.participate(participationRequest, authentication.getName());
        return ResponseDto.of(OK.value(), SUCCESS_ADD_MANAGER.getMessage(), response);
    }

    @DeleteMapping("/participation")
    @Operation(summary = "동아리 관리자 삭제")
    public ResponseDto removeManager(@RequestBody RemoveManagerRequest removeManagerRequest) {
        clubManageUseCase.removeManager(removeManagerRequest);
        return ResponseDto.of(OK.value(), SUCCESS_REMOVE_MANAGER.getMessage());
    }

    @GetMapping("/participation/code/{id}")
    @Operation(summary = "동아리 관리자 참여 코드 조회")
    public ResponseDto<ParticipationCodeResponse> getParticipationCode(@PathVariable String id) {
        ParticipationCodeResponse response = participationCodeService.getCode(id);
        return ResponseDto.of(OK.value(), SUCCESS_GET_CODE.getMessage(), response);
    }

    @PostMapping("/participation/code/{id}")
    @Operation(summary = "동아리 관리자 참여 코드 재생성")
    public ResponseDto<ParticipationCodeResponse> regenerateParticipationCode(@PathVariable String id) {
        ParticipationCodeResponse response = participationCodeService.regenerate(id);
        return ResponseDto.of(OK.value(), SUCCESS_REGENERATE_CODE.getMessage(), response);
    }
}
