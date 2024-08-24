package com.yoyomo.domain.application.application.usecase;

import com.yoyomo.domain.application.application.dto.response.ApplicationResponseDTO.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.yoyomo.domain.application.application.dto.request.ApplicationRequestDTO.Stage;
import static com.yoyomo.domain.application.application.dto.response.ApplicationResponseDTO.Detail;

public interface ApplicationManageUseCase {

    Detail read(String applicationId, Long userId);

    List<Response> search(String name, String recruitmentId, Long userId);

    Page<Detail> readAll(String recruitmentId, Integer stage, Long userId, Pageable pageable);

    void update(Stage dto, Long userId, String recruitmentId);
}
