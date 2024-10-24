package com.yoyomo.domain.club.presentation;

import com.yoyomo.domain.club.application.dto.request.ClubRequestDTO;
import com.yoyomo.domain.club.application.dto.response.ClubResponseDTO;
import com.yoyomo.domain.club.application.usecase.ClubManageUseCase;
import com.yoyomo.global.common.annotation.CurrentUser;
import com.yoyomo.global.common.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

import static com.yoyomo.domain.club.application.dto.request.ClubRequestDTO.*;
import static com.yoyomo.domain.club.application.dto.request.ClubRequestDTO.Save;
import static com.yoyomo.domain.club.application.dto.response.ClubResponseDTO.*;
import static com.yoyomo.domain.club.presentation.constant.ResponseMessage.*;
import static com.yoyomo.domain.user.application.dto.response.ManagerResponseDTO.ManagerInfo;
import static org.springframework.http.HttpStatus.OK;

@Tag(name = "CLUB")
@RestController
@RequiredArgsConstructor
@RequestMapping("/clubs")
public class ClubController {

    private final ClubManageUseCase clubManageUseCase;

    @PostMapping
    @Operation(summary = "동아리 생성")
    public ResponseDto<Response> save(@RequestBody @Valid Save dto, @CurrentUser @Parameter(hidden = true) Long userId) throws IOException {
        return ResponseDto.of(OK.value(), SUCCESS_SAVE.getMessage(), clubManageUseCase.save(dto, userId));
    }

    @GetMapping("/{clubId}")
    @Operation(summary = "동아리 조회")
    public ResponseDto<Response> read(@PathVariable String clubId) {
        return ResponseDto.of(OK.value(), SUCCESS_READ.getMessage(), clubManageUseCase.read(clubId));
    }

    @GetMapping
    @Operation(summary = "워크 스페이스 조회")
    public ResponseDto<List<Response>> readAll(@CurrentUser @Parameter(hidden = true) Long userId) {
        return ResponseDto.of(OK.value(), SUCCESS_READ.getMessage(), clubManageUseCase.readAll(userId));
    }

    @PatchMapping("/{clubId}")
    @Operation(summary = "동아리 수정")
    public ResponseDto<Void> update(@PathVariable String clubId, @RequestBody @Valid Update dto , @CurrentUser @Parameter(hidden = true) Long userId) {
        clubManageUseCase.update(clubId, dto, userId);
        return ResponseDto.of(OK.value(), SUCCESS_UPDATE.getMessage());
    }

    @DeleteMapping("/{clubId}")
    @Operation(summary = "동아리 삭제")
    public ResponseDto<Void> delete(@PathVariable String clubId , @CurrentUser @Parameter(hidden = true) Long userId) {
        clubManageUseCase.delete(clubId, userId);
        return ResponseDto.of(OK.value(), SUCCESS_DELETE.getMessage());
    }

    @GetMapping("/participation/{clubId}")
    @Operation(summary = "동아리 관리자 조회")
    public ResponseDto<List<ManagerInfo>> getManagers(@PathVariable String clubId, @CurrentUser @Parameter(hidden = true) Long userId) {
        return ResponseDto.of(OK.value(), SUCCESS_GET_MANAGERS.getMessage(), clubManageUseCase.getManagers(clubId, userId));
    }

    @PostMapping("/participation")
    @Operation(summary = "동아리 관리자 추가")
    public ResponseDto<ClubResponseDTO.Participation> participation(@RequestBody @Valid ClubRequestDTO.Participation dto, @CurrentUser @Parameter(hidden = true) Long userId) {
        return ResponseDto.of(OK.value(), SUCCESS_PARTICIPATION.getMessage(), clubManageUseCase.participate(dto, userId));
    }

    @DeleteMapping("/participation")
    @Operation(summary = "동아리 관리자 삭제")
    public ResponseDto<Void> deleteManagers(@RequestBody @Valid Delete dto, @CurrentUser @Parameter(hidden = true) Long userId) {
        clubManageUseCase.deleteManagers(dto, userId);
        return ResponseDto.of(OK.value(), SUCCESS_DELETE_MANAGERS.getMessage());
    }

    @GetMapping("/participation/code/{clubId}")
    @Operation(summary = "동아리 관리자 참여 코드 조회")
    public ResponseDto<Code> readCode(@PathVariable String clubId, @CurrentUser @Parameter(hidden = true) Long userId) {
        return ResponseDto.of(OK.value(), SUCCESS_READ_CODE.getMessage(), clubManageUseCase.readCode(clubId, userId));
    }

    @PatchMapping("/participation/code/{clubId}")
    @Operation(summary = "동아리 관리자 참여 코드 재생성")
    public ResponseDto<Code> updateCode(@PathVariable String clubId, @CurrentUser @Parameter(hidden = true) Long userId) {
        return ResponseDto.of(OK.value(), SUCCESS_UPDATE_CODE.getMessage(), clubManageUseCase.updateCode(clubId, userId));
    }
}
