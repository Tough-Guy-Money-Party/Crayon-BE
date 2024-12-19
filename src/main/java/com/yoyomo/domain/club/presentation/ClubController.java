package com.yoyomo.domain.club.presentation;

import static com.yoyomo.domain.club.application.dto.request.ClubRequestDTO.Delete;
import static com.yoyomo.domain.club.application.dto.request.ClubRequestDTO.Save;
import static com.yoyomo.domain.club.application.dto.request.ClubRequestDTO.Update;
import static com.yoyomo.domain.club.application.dto.response.ClubResponseDTO.Code;
import static com.yoyomo.domain.club.application.dto.response.ClubResponseDTO.Response;
import static com.yoyomo.domain.club.presentation.constant.ResponseMessage.SUCCESS_DELETE;
import static com.yoyomo.domain.club.presentation.constant.ResponseMessage.SUCCESS_DELETE_MANAGERS;
import static com.yoyomo.domain.club.presentation.constant.ResponseMessage.SUCCESS_GET_MANAGERS;
import static com.yoyomo.domain.club.presentation.constant.ResponseMessage.SUCCESS_PARTICIPATION;
import static com.yoyomo.domain.club.presentation.constant.ResponseMessage.SUCCESS_READ;
import static com.yoyomo.domain.club.presentation.constant.ResponseMessage.SUCCESS_READ_CODE;
import static com.yoyomo.domain.club.presentation.constant.ResponseMessage.SUCCESS_SAVE;
import static com.yoyomo.domain.club.presentation.constant.ResponseMessage.SUCCESS_UPDATE;
import static com.yoyomo.domain.club.presentation.constant.ResponseMessage.SUCCESS_UPDATE_CODE;
import static com.yoyomo.domain.club.presentation.constant.ResponseMessage.SUCCESS_UPDATE_MANAGERS;
import static com.yoyomo.domain.user.application.dto.response.UserResponseDTO.ManagerInfo;
import static org.springframework.http.HttpStatus.OK;

import com.yoyomo.domain.club.application.dto.request.ClubManagerUpdateDto;
import com.yoyomo.domain.club.application.dto.request.ClubRequestDTO;
import com.yoyomo.domain.club.application.dto.response.ClubResponseDTO.Participation;
import com.yoyomo.domain.club.application.usecase.ClubConfigureUseCase;
import com.yoyomo.domain.club.application.usecase.ClubManagerUseCase;
import com.yoyomo.domain.club.application.usecase.ClubReadUseCase;
import com.yoyomo.global.common.annotation.CurrentUser;
import com.yoyomo.global.common.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "CLUB")
@RestController
@RequiredArgsConstructor
@RequestMapping("/clubs")
public class ClubController {

    private final ClubConfigureUseCase clubManageUseCase;
    private final ClubReadUseCase clubReadUseCase;
    private final ClubManagerUseCase clubManagerUseCase;

    @PostMapping
    @Operation(summary = "동아리 생성")
    public ResponseDto<Response> save(@RequestBody @Valid Save dto, @CurrentUser @Parameter(hidden = true) Long userId)
            throws IOException {
        Response response = clubManageUseCase.save(dto, userId);

        return ResponseDto.of(OK.value(), SUCCESS_SAVE.getMessage(), response);
    }

    @GetMapping("/{clubId}")
    @Operation(summary = "동아리 조회")
    public ResponseDto<Response> read(@PathVariable String clubId) {
        Response response = clubReadUseCase.read(clubId);

        return ResponseDto.of(OK.value(), SUCCESS_READ.getMessage(), response);
    }

    @GetMapping
    @Operation(summary = "워크 스페이스 조회")
    public ResponseDto<List<Response>> readAll(@CurrentUser @Parameter(hidden = true) Long userId) {
        List<Response> responses = clubReadUseCase.readAll(userId);

        return ResponseDto.of(OK.value(), SUCCESS_READ.getMessage(), responses);
    }

    @PatchMapping("/{clubId}")
    @Operation(summary = "동아리 수정")
    public ResponseDto<Void> update(@PathVariable UUID clubId, @RequestBody @Valid Update dto,
                                    @CurrentUser @Parameter(hidden = true) Long userId) {
        clubManageUseCase.update(clubId, dto, userId);

        return ResponseDto.of(OK.value(), SUCCESS_UPDATE.getMessage());
    }

    @DeleteMapping("/{clubId}")
    @Operation(summary = "동아리 삭제")
    public ResponseDto<Void> delete(@PathVariable UUID clubId, @CurrentUser @Parameter(hidden = true) Long userId) {
        clubManageUseCase.delete(clubId, userId);

        return ResponseDto.of(OK.value(), SUCCESS_DELETE.getMessage());
    }

    @GetMapping("/participation/{clubId}")
    @Operation(summary = "동아리 관리자 조회")
    public ResponseDto<List<ManagerInfo>> getManagers(@PathVariable UUID clubId,
                                                      @CurrentUser @Parameter(hidden = true) Long userId) {
        List<ManagerInfo> managers = clubManagerUseCase.getManagers(clubId, userId);

        return ResponseDto.of(OK.value(), SUCCESS_GET_MANAGERS.getMessage(), managers);
    }

    @PostMapping("/participation")
    @Operation(summary = "동아리 관리자 추가")
    public ResponseDto<Participation> participation(@RequestBody @Valid ClubRequestDTO.Participation dto,
                                                    @CurrentUser @Parameter(hidden = true) Long userId) {
        Participation manager = clubManagerUseCase.participate(dto, userId);

        return ResponseDto.of(OK.value(), SUCCESS_PARTICIPATION.getMessage(), manager);
    }

    @DeleteMapping("/participation")
    @Operation(summary = "동아리 관리자 삭제")
    public ResponseDto<Void> deleteManagers(@RequestBody @Valid Delete dto,
                                            @CurrentUser @Parameter(hidden = true) Long userId) {
        clubManagerUseCase.deleteManagers(dto, userId);

        return ResponseDto.of(OK.value(), SUCCESS_DELETE_MANAGERS.getMessage());
    }

    @GetMapping("/participation/code/{clubId}")
    @Operation(summary = "동아리 관리자 참여 코드 조회")
    public ResponseDto<Code> readCode(@PathVariable String clubId, @CurrentUser @Parameter(hidden = true) Long userId) {
        Code code = clubManagerUseCase.readCode(clubId, userId);

        return ResponseDto.of(OK.value(), SUCCESS_READ_CODE.getMessage(), code);
    }

    @PatchMapping("/participation/code/{clubId}")
    @Operation(summary = "동아리 관리자 참여 코드 재생성")
    public ResponseDto<Code> updateCode(@PathVariable String clubId,
                                        @CurrentUser @Parameter(hidden = true) Long userId) {
        Code code = clubManagerUseCase.updateCode(clubId, userId);

        return ResponseDto.of(OK.value(), SUCCESS_UPDATE_CODE.getMessage(), code);
    }

    @PatchMapping("/{clubId}/owner")
    @Operation(summary = "동아리 권한 이전")
    public ResponseDto<Void> updateOwner(@RequestBody ClubManagerUpdateDto dto,
                                         @PathVariable UUID clubId,
                                         @CurrentUser @Parameter(hidden = true) Long userId) {

        clubManagerUseCase.updateOwner(dto, userId, clubId);
        return ResponseDto.of(OK.value(), SUCCESS_UPDATE_MANAGERS.getMessage());
    }
}
