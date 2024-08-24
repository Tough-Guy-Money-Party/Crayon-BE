package com.yoyomo.domain.application.application.usecase;

import com.yoyomo.domain.application.application.dto.request.ApplicationRequestDTO;
import com.yoyomo.domain.application.application.dto.response.ApplicationResponseDTO.Detail;

import java.util.List;

import static com.yoyomo.domain.application.application.dto.response.ApplicationResponseDTO.MyResponse;
import static com.yoyomo.domain.user.application.dto.request.UserRequestDTO.Find;

public interface ApplyUseCase {

    void apply(ApplicationRequestDTO.Save dto, String recruitmentId);

    List<MyResponse> readAll(Find dto);

    Detail read(String applicationId);
}
