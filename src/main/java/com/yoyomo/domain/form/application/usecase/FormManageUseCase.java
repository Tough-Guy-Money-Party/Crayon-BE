package com.yoyomo.domain.form.application.usecase;

import static com.yoyomo.domain.form.application.dto.response.FormResponseDTO.DetailResponse;
import static com.yoyomo.domain.form.application.dto.response.FormResponseDTO.Info;
import static com.yoyomo.domain.form.application.dto.response.FormResponseDTO.Response;
import static com.yoyomo.domain.form.application.dto.response.FormResponseDTO.SaveResponse;

import com.yoyomo.domain.form.application.dto.request.FormRequestDTO;
import com.yoyomo.domain.form.application.dto.request.FormRequestDTO.Update;
import java.util.List;

public interface FormManageUseCase {
    DetailResponse read(String id);

    Info readForm(String id);

    List<Response> readAll(Long userId, String clubId);

    SaveResponse create(FormRequestDTO.Save dto, String clubId, Long userId);

    void update(String id, Update dto, Long userId);

    void delete(String formId, Long userId);

    List<Response> search(String keyword, String clubId, Long userId);
}
