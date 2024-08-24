package com.yoyomo.domain.application.application.usecase;

import com.yoyomo.domain.application.application.dto.request.ApplicationRequestDTO.Update;
import com.yoyomo.domain.application.application.dto.response.ApplicationResponseDTO.Response;

import java.util.List;

import static com.yoyomo.domain.application.application.dto.request.ApplicationRequestDTO.Save;
import static com.yoyomo.domain.application.application.dto.response.ApplicationResponseDTO.MyResponse;
import static com.yoyomo.domain.user.application.dto.request.UserRequestDTO.Find;

public interface ApplyUseCase {

    void apply(Save dto, String recruitmentId);

    List<Response> readAll(Find dto);

    MyResponse read(String applicationId);

    void update(String applicationId, Update dto);
}
