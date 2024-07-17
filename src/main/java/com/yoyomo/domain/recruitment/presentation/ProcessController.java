package com.yoyomo.domain.recruitment.presentation;

import com.yoyomo.domain.recruitment.application.dto.res.ProcessResponse;
import com.yoyomo.domain.recruitment.application.usecase.ProcessUseCase;
import com.yoyomo.global.config.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.yoyomo.domain.recruitment.presentation.constant.ResponseMessage.SUCCESS_READ;
import static org.springframework.http.HttpStatus.OK;

@Tag(name = "PROCESS")
@RestController
@RequiredArgsConstructor
@RequestMapping("/processes")
public class ProcessController {

    private final ProcessUseCase processUseCase;

    @GetMapping("/{recruitmentId}")
    @Operation(summary = "지원 프로세스 목록 조회")
    public ResponseDto<List<ProcessResponse>> readAllProcess(@PathVariable String recruitmentId) {
        return ResponseDto.of(OK.value(), SUCCESS_READ.getMessage(), processUseCase.readAll(recruitmentId));
    }
}
