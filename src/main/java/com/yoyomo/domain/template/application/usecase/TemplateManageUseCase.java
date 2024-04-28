package com.yoyomo.domain.template.application.usecase;

import com.yoyomo.domain.form.application.dto.req.FormRequest;
import com.yoyomo.domain.form.application.dto.req.FormUpdateRequest;
import com.yoyomo.domain.form.application.dto.res.FormCreateResponse;
import com.yoyomo.domain.form.application.dto.res.FormDetailsResponse;
import com.yoyomo.domain.form.application.dto.res.FormResponse;
import com.yoyomo.domain.template.application.dto.req.TemplateRequest;
import com.yoyomo.domain.template.application.dto.req.TemplateUpdateRequest;
import com.yoyomo.domain.template.application.dto.res.TemplateCreateResponse;
import com.yoyomo.domain.template.application.dto.res.TemplateDetailsResponse;
import com.yoyomo.domain.template.application.dto.res.TemplateResponse;
import com.yoyomo.domain.template.domain.entity.Template;

import java.util.List;

public interface TemplateManageUseCase {
    TemplateDetailsResponse read(String id);

    List<TemplateResponse> readAll(String clubId);

    TemplateCreateResponse create(TemplateRequest request);

    void update(String id, TemplateUpdateRequest request);

    void delete(String templateId);
}
