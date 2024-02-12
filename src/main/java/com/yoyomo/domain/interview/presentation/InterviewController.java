package com.yoyomo.domain.interview.presentation;

import com.yoyomo.domain.interview.application.dto.InterviewRequest;
import com.yoyomo.domain.interview.application.usecase.InterviewManageUseCase;
import com.yoyomo.global.config.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.yoyomo.domain.interview.presentation.constant.ResponseMessage.SUCCESS_CREATE;
import static org.springframework.http.HttpStatus.CREATED;

@Tag(name = "INTERVIEW")
@RestController
@RequiredArgsConstructor
@RequestMapping("/applications/{applicationId}")
public class InterviewController {
    private final InterviewManageUseCase interviewManageUseCase;

    @PostMapping("/interview")
    @Operation(summary = "면접 생성")
    public ResponseDto<Void> create(@PathVariable String applicationId, @RequestBody InterviewRequest request) {
        interviewManageUseCase.create(applicationId, request);
        return ResponseDto.of(CREATED.value(), SUCCESS_CREATE.getMessage());
    }
}
