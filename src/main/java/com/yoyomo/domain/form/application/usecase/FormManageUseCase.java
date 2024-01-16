package com.yoyomo.domain.form.application.usecase;

import com.yoyomo.domain.form.application.dto.req.FormRequest;
import com.yoyomo.domain.form.application.dto.res.FormCreateResponse;
import com.yoyomo.domain.form.application.dto.res.FormResponse;

import java.util.List;

public interface FormManageUseCase {
    FormResponse read(String id);

    List<FormResponse> read();

    FormCreateResponse create(FormRequest request);
}
