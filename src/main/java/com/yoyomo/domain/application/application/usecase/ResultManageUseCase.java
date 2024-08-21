package com.yoyomo.domain.application.application.usecase;

import com.yoyomo.domain.application.application.dto.response.ResultResponseDTO.Result;

import java.util.List;

import static com.yoyomo.domain.user.application.dto.request.UserRequestDTO.Announce;

public interface ResultManageUseCase {
    List<Result> announce(Announce dto);
}
