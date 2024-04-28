package com.yoyomo.domain.template.domain.service;

import com.yoyomo.domain.form.domain.entity.Form;
import com.yoyomo.domain.template.domain.entity.Template;
import com.yoyomo.domain.template.domain.repository.TemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TemplateSaveService {
    private final TemplateRepository templateRepository;

    public Template save(Template template) {
        return templateRepository.save(template);
    }
}