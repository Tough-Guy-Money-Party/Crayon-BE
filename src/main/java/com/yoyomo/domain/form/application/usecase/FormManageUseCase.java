package com.yoyomo.domain.form.application.usecase;

import com.yoyomo.domain.form.application.dto.request.FormRequestDTO;
import com.yoyomo.domain.form.application.dto.request.FormRequestDTO.Update;
import com.yoyomo.domain.form.application.dto.response.FormResponseDTO.Response;

import java.util.List;

import static com.yoyomo.domain.form.application.dto.response.FormResponseDTO.DetailResponse;
import static com.yoyomo.domain.form.application.dto.response.FormResponseDTO.SaveResponse;

public interface FormManageUseCase {
    DetailResponse read(String id);

    List<Response> readAll(Long userId, String clubId);

    SaveResponse create(FormRequestDTO.Save dto, String clubId, Long userId);

    void update(String id, Update dto, Long userId);

    void delete(String formId, Long userId);

    List<Response> search(String keyword, String clubId, Long userId);
}
