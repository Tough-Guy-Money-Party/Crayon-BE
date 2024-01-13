package com.yoyomo.domain.form.application.usecase;

import com.yoyomo.domain.form.application.dto.req.FormRequest;
import com.yoyomo.domain.form.application.dto.res.FormCreateResponse;
import com.yoyomo.domain.form.application.mapper.FormMapper;
import com.yoyomo.domain.form.domain.entity.Form;
import com.yoyomo.domain.form.domain.service.FormSaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FormManageUseCaseImpl implements FormManageUseCase {
    private final FormSaveService formSaveService;
    private final FormMapper formMapper;

    @Override
    public FormCreateResponse create(FormRequest request) {
        Form form = formMapper.from(request);
        String id = formSaveService.save(form).getId();
        return new FormCreateResponse(id);
    }
}
