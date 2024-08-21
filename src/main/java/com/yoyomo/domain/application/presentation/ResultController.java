package com.yoyomo.domain.application.presentation;

import com.yoyomo.domain.application.application.dto.response.ResultResponseDTO.Result;
import com.yoyomo.domain.application.application.usecase.ResultManageUseCase;
import com.yoyomo.domain.user.application.dto.request.UserRequestDTO.Announce;
import com.yoyomo.global.common.dto.ResponseDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping
    public ResponseDto<List<Result>> announce(@RequestBody Announce dto) {
        return ResponseDto.of(OK.value(), SUCCESS_READ_RESULT.getMessage(), resultManageUseCase.announce(dto));
    }
}
