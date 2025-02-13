package com.yoyomo.domain.form.presentation;


import com.yoyomo.domain.form.application.dto.request.FormRequestDTO.Update;
import com.yoyomo.domain.form.application.dto.response.FormDetailResponse;
import com.yoyomo.domain.form.application.dto.response.FormResponseDTO.Response;
import com.yoyomo.domain.form.application.usecase.FormManageUseCase;
import com.yoyomo.domain.user.domain.entity.User;
import com.yoyomo.global.common.annotation.CurrentUser;
import com.yoyomo.global.common.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

import static com.yoyomo.domain.form.application.dto.request.FormRequestDTO.Save;
import static com.yoyomo.domain.form.application.dto.response.FormResponseDTO.DetailResponse;
import static com.yoyomo.domain.form.application.dto.response.FormResponseDTO.SaveResponse;
import static com.yoyomo.domain.form.presentation.constant.ResponseMessage.SUCCESS_DELETE;
import static com.yoyomo.domain.form.presentation.constant.ResponseMessage.SUCCESS_READ;
import static com.yoyomo.domain.form.presentation.constant.ResponseMessage.SUCCESS_SEARCH;
import static com.yoyomo.domain.form.presentation.constant.ResponseMessage.SUCCESS_UPDATE;
import static com.yoyomo.domain.item.presentation.constant.ResponseMessage.SUCCESS_CREATE;
import static org.springframework.http.HttpStatus.OK;

@Tag(name = "FORM")
@RestController
@RequiredArgsConstructor
@RequestMapping("/forms")
public class FormController {

    private final FormManageUseCase formManageUseCase;

    @PostMapping("/{clubId}")
    @Operation(summary = "폼 생성")    // 수정: 이미지 로직 추가
    public ResponseDto<SaveResponse> save(@RequestBody @Valid Save dto,
                                          @PathVariable String clubId,
                                          @CurrentUser User user) {
        SaveResponse response = formManageUseCase.create(dto, clubId, user);

        return ResponseDto.of(OK.value(), SUCCESS_CREATE.getMessage(), response);
    }

    @GetMapping("/{formId}")
    @Operation(summary = "폼 상세 조회")
    public ResponseDto<DetailResponse> read(@PathVariable String formId) {
        DetailResponse response = formManageUseCase.read(formId);

        return ResponseDto.of(OK.value(), SUCCESS_READ.getMessage(), response);
    }

    @GetMapping("/all/{clubId}")
    @Operation(summary = "내 동아리의 폼 목록 조회")
    public ResponseDto<List<Response>> readAll(@CurrentUser User user, @PathVariable String clubId) {
        List<Response> responses = formManageUseCase.readAll(user, clubId);

        return ResponseDto.of(OK.value(), SUCCESS_READ.getMessage(), responses);
    }

    @PutMapping("/{formId}")
    @Operation(summary = "폼 수정")
    public ResponseDto<Void> update(@PathVariable String formId, @RequestBody @Valid Update dto, @CurrentUser User user) {
        formManageUseCase.update(formId, dto, user);

        return ResponseDto.of(OK.value(), SUCCESS_UPDATE.getMessage());
    }

    @DeleteMapping("/{formId}")
    @Operation(summary = "폼 삭제")
    public ResponseDto<Void> delete(@PathVariable String formId, @CurrentUser User user) {
        formManageUseCase.delete(formId, user);

        return ResponseDto.of(OK.value(), SUCCESS_DELETE.getMessage());
    }

    @GetMapping("/{clubId}/search")
    @Operation(summary = "내 동아리의 폼 목록 검색")
    public ResponseDto<List<Response>> search(@RequestParam String keyword, @PathVariable String clubId, @CurrentUser User user) {
        List<Response> responses = formManageUseCase.search(keyword, clubId, user);

        return ResponseDto.of(OK.value(), SUCCESS_SEARCH.getMessage(), responses);
    }

    @GetMapping
    @Operation(summary = "지원서 폼 조회")
    public ResponseDto<FormDetailResponse> read(@RequestParam UUID recruitmentId,
                                                @CurrentUser User user) {
        FormDetailResponse response = formManageUseCase.read(recruitmentId, user);

        return ResponseDto.of(OK.value(), SUCCESS_READ.getMessage(), response);
    }

    @PostMapping("/replication/{formId}")
    @Operation(summary = "지원서 양식 복제")
    public ResponseDto<Void> replicate(@PathVariable String formId,
                                       @CurrentUser User user) {
        formManageUseCase.replicate(formId, user);
        return ResponseDto.of(OK.value(), SUCCESS_CREATE.getMessage());
    }
}
