package com.yoyomo.domain.club.presentation;

import com.yoyomo.domain.club.application.usecase.ClubManageUseCase;
import com.yoyomo.global.common.annotation.CurrentUser;
import com.yoyomo.global.common.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.yoyomo.domain.club.application.dto.request.ClubRequestDTO.Save;
import static com.yoyomo.domain.club.application.dto.response.ClubResponseDTO.Response;
import static com.yoyomo.domain.club.presentation.constant.ResponseMessage.SUCCESS_READ;
import static com.yoyomo.domain.club.presentation.constant.ResponseMessage.SUCCESS_SAVE;
import static org.springframework.http.HttpStatus.OK;

@Tag(name = "CLUB")
@RestController
@RequiredArgsConstructor
@RequestMapping("/clubs")
public class ClubController {

    private final ClubManageUseCase clubManageUseCase;

    @PostMapping
    @Operation(summary = "동아리 생성")
    public ResponseDto<Response> save(@RequestBody @Valid Save dto, @CurrentUser @Parameter(hidden = true) Long userId) {
        return ResponseDto.of(OK.value(), SUCCESS_SAVE.getMessage(), clubManageUseCase.save(dto, userId));
    }

    @GetMapping("/{clubId}")
    @Operation(summary = "동아리 조회")
    public ResponseDto<Response> read(@PathVariable String clubId) {
        return ResponseDto.of(OK.value(), SUCCESS_READ.getMessage(), clubManageUseCase.read(clubId));
    }
}
