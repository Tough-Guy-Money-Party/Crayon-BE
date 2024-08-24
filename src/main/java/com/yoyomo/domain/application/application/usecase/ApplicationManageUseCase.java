package com.yoyomo.domain.application.application.usecase;

import com.yoyomo.domain.application.application.dto.response.ApplicationResponseDTO.Response;

import java.util.List;

import static com.yoyomo.domain.application.application.dto.response.ApplicationResponseDTO.Detail;

public interface ApplicationManageUseCase {
    Detail read(String applicationId, Long userId);

    List<Response> search(String name, String recruitmentId, Long userId);

}
