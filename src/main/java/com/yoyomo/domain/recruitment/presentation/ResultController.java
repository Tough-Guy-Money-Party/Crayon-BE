package com.yoyomo.domain.recruitment.presentation;

import com.yoyomo.domain.recruitment.application.dto.res.ProcessResultsResponse;
import com.yoyomo.domain.recruitment.application.usecase.ResultConfirmUseCase;
import com.yoyomo.global.config.dto.ResponseDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.yoyomo.domain.recruitment.presentation.constant.ResponseMessage.RESULT_SUCCESS_READ;
import static org.springframework.http.HttpStatus.OK;

@Tag(name = "RESULT")
@RestController
@RequiredArgsConstructor
@RequestMapping("/results")
public class ResultController {

    private final ResultConfirmUseCase resultConfirmUseCase;

    @GetMapping("/{clubId}")
    public ResponseDto<List<ProcessResultsResponse>> getClubResults(@PathVariable String clubId) {
        List<ProcessResultsResponse> response = resultConfirmUseCase.read(clubId);

        return ResponseDto.of(OK.value(), RESULT_SUCCESS_READ.getMessage(), response);
    }
}
