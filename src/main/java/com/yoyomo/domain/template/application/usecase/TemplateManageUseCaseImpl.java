package com.yoyomo.domain.template.application.usecase;

import com.yoyomo.domain.template.application.dto.req.TemplateRequest;
import com.yoyomo.domain.template.application.dto.req.TemplateUpdateRequest;
import com.yoyomo.domain.template.application.dto.res.TemplateCreateResponse;
import com.yoyomo.domain.template.application.dto.res.TemplateDetailsResponse;
import com.yoyomo.domain.template.application.dto.res.TemplateResponse;
import com.yoyomo.domain.template.application.mapper.TemplateMapper;
import com.yoyomo.domain.template.domain.entity.Template;
import com.yoyomo.domain.template.domain.service.TemplateGetService;
import com.yoyomo.domain.template.domain.service.TemplateSaveService;
import com.yoyomo.domain.template.domain.service.TemplateUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TemplateManageUseCaseImpl implements TemplateManageUseCase {
    private final TemplateGetService templateGetService;
    private final TemplateSaveService templateSaveService;
    private final TemplateUpdateService templateUpdateService;
    private final TemplateMapper templateMapper;

    @Override
    public TemplateDetailsResponse read(String id) {
        Template template = templateGetService.find(id);
        return new TemplateDetailsResponse(template.getName(), template.getPassText(), template.getFailText());
    }

    @Override
    public List<TemplateResponse> readAll(String clubId) {
        List<Template> templates = templateGetService.findAll(clubId);
        return templates.stream()
                .map(templateMapper::mapToTemplateResponse)
                .toList();
    }

    @Override
    public TemplateCreateResponse create(TemplateRequest request) {
        Template template = templateMapper.from(request);
        String id = templateSaveService.save(template).getId();
        return new TemplateCreateResponse(id);
    }

    @Override
    public void update(String id, TemplateUpdateRequest request) {
        templateUpdateService.update(id, request.name(), request.passText(), request.failText());
    }

    @Override
    public void delete(String id) {
        templateUpdateService.delete(id);
    }
}
