package com.yoyomo.domain.form.application.usecase;

import com.yoyomo.domain.form.application.dto.req.FormRequest;
import com.yoyomo.domain.form.application.dto.req.FormRequestDTO;
import com.yoyomo.domain.form.application.dto.req.FormUpdateRequest;
import com.yoyomo.domain.form.application.dto.res.FormCreateResponse;
import com.yoyomo.domain.form.application.dto.res.FormDetailsResponse;
import com.yoyomo.domain.form.application.dto.res.FormResponse;
import com.yoyomo.domain.form.application.dto.res.FormResponseDTO;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface FormManageUseCase {
    FormDetailsResponse read(String id);

    List<FormResponse> readAll(Authentication authentication);

    FormResponseDTO.SaveResponse create(FormRequestDTO.Save dto, String clubId, Long userId);

    void update(String id, FormUpdateRequest request);

    void update(String id, Boolean enabled);

    void delete(String formId);

    List<FormResponse> searchByKeyword(String keyword, Authentication authentication);
}
