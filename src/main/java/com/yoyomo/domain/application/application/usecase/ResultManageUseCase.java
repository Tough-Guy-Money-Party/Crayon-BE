package com.yoyomo.domain.application.application.usecase;

import com.yoyomo.domain.application.application.dto.response.ResultResponseDTO.Result;
import com.yoyomo.domain.user.application.dto.request.UserRequestDTO.Find;

import java.util.List;

public interface ResultManageUseCase {
    List<Result> announce(Find dto);
}
