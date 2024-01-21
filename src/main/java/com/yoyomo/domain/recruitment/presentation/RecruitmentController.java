package com.yoyomo.domain.recruitment.presentation;

import com.yoyomo.domain.recruitment.application.dto.req.RecruitmentRequest;
import com.yoyomo.domain.recruitment.application.usecase.RecruitmentManageUseCase;
import com.yoyomo.global.config.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.yoyomo.domain.recruitment.presentation.constant.ResponseMessage.SUCCESS_CREATE;
import static org.springframework.http.HttpStatus.CREATED;

@Tag(name = "RECRUITMENT")
@RestController
@RequiredArgsConstructor
@RequestMapping("/form/{formId}")
public class RecruitmentController {
    private final RecruitmentManageUseCase recruitmentManageUseCase;

    @PostMapping
    @Operation(summary = "모집 생성")
    public ResponseDto create(@PathVariable String formId, @RequestBody RecruitmentRequest request) {
        recruitmentManageUseCase.create(formId, request);
        return ResponseDto.of(CREATED.value(), SUCCESS_CREATE.getMessage());
    }
}
