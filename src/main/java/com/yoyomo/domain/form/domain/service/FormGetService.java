package com.yoyomo.domain.form.domain.service;

import com.yoyomo.domain.form.domain.entity.Form;
import com.yoyomo.domain.form.domain.repository.FormRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FormGetService {
    private final FormRepository formRepository;

    public Form find(String id) {
        return formRepository.findById(id).orElseThrow();
    }
}
