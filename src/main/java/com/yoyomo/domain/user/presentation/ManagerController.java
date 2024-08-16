package com.yoyomo.domain.user.presentation;

import com.yoyomo.domain.user.application.usecase.ManagerManageUseCase;
import com.yoyomo.global.common.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.yoyomo.domain.user.application.dto.response.ManagerResponseDTO.Response;
import static com.yoyomo.domain.user.presentation.constant.ResponseMessage.SUCCESS_LOGIN;
import static org.springframework.http.HttpStatus.OK;

@Tag(name = "USER")
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class ManagerController {

    private final ManagerManageUseCase managerManageUseCase;

    @PostMapping(value = "/login/{code}")
    @Operation(summary = "카카오 로그인 및 회원가입")
    public ResponseDto<Response> authenticate(@PathVariable String code) {
        Response response = managerManageUseCase.authenticate(code);
        return ResponseDto.of(OK.value(), SUCCESS_LOGIN.getMessage(), response);
    }
}
