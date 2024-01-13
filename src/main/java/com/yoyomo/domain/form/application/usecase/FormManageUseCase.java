package com.yoyomo.domain.form.application.usecase;

import com.yoyomo.domain.form.application.dto.req.FormRequest;
import com.yoyomo.domain.form.application.dto.res.FormCreateResponse;
import com.yoyomo.domain.form.application.dto.res.FormResponse;

public interface FormManageUseCase {
    FormResponse read(String id);

    FormCreateResponse create(FormRequest request);
}
