package com.yoyomo.domain.application.application.usecase;

import com.yoyomo.domain.application.application.dto.response.ApplicationResponseDTO.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import static com.yoyomo.domain.application.application.dto.request.ApplicationRequestDTO.Stage;
import static com.yoyomo.domain.application.application.dto.request.InterviewRequestDTO.Save;
import static com.yoyomo.domain.application.application.dto.response.ApplicationResponseDTO.Detail;

public interface ApplicationManageUseCase {

    Detail read(String applicationId, Long userId);

    Page<Response> search(String name, String recruitmentId, Long userId, Pageable pageable);

    Page<Detail> readAll(String recruitmentId, Integer stage, Long userId, Pageable pageable);

    void update(Stage dto, Long userId, String recruitmentId);

    void saveInterview(String applicationId, Save dto, Long userId);
}
