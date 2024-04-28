package com.yoyomo.domain.template.domain.service;

import com.yoyomo.domain.form.domain.entity.Form;
import com.yoyomo.domain.form.domain.repository.FormRepository;
import com.yoyomo.domain.form.exception.FormNotFoundException;
import com.yoyomo.domain.template.domain.entity.Template;
import com.yoyomo.domain.template.domain.repository.TemplateRepository;
import com.yoyomo.domain.template.exception.TemplateNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TemplateGetService {
    private final TemplateRepository templateRepository;

    public Template find(String id) {
        return templateRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(TemplateNotFoundException::new);
    }

    public List<Template> findAll(String clubId) {
        return templateRepository.findAllByClubIdAndDeletedAtIsNull(clubId);
    }
}