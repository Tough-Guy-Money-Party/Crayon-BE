package com.yoyomo.domain.application.presentation;

import com.yoyomo.domain.application.application.dto.response.ResultResponseDTO.Result;
import com.yoyomo.domain.application.application.usecase.ResultManageUseCase;
import com.yoyomo.domain.user.application.dto.request.UserRequestDTO.Find;
import com.yoyomo.global.common.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.yoyomo.domain.application.presentation.constant.ResponseMessage.SUCCESS_READ_RESULT;
import static org.springframework.http.HttpStatus.OK;

@Tag(name = "RESULT")
@RestController
@RequiredArgsConstructor
@RequestMapping("/result")
public class ResultController {

    private final ResultManageUseCase resultManageUseCase;

    @Operation(summary = "[Result] 사용자가 결과를 조회합니다.")
    @PostMapping
    public ResponseDto<List<Result>> announce(@RequestBody @Valid Find dto) {
        return ResponseDto.of(OK.value(), SUCCESS_READ_RESULT.getMessage(), resultManageUseCase.announce(dto));
    }
}
