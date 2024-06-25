package com.yoyomo.domain.form.application.usecase;

import com.yoyomo.domain.form.application.dto.req.FormRequest;
import com.yoyomo.domain.form.application.dto.req.FormUpdateRequest;
import com.yoyomo.domain.form.application.dto.res.FormCreateResponse;
import com.yoyomo.domain.form.application.dto.res.FormDetailsResponse;
import com.yoyomo.domain.form.application.dto.res.FormResponse;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface FormManageUseCase {
    FormDetailsResponse read(String id);

    List<FormResponse> readAll(String clubId);

    FormCreateResponse create(FormRequest request, Authentication authentication);

    void update(String id, FormUpdateRequest request);

    void delete(String formId);

    List<FormResponse> searchByKeyword(String keyword, Authentication authentication);
}
