package com.yoyomo.domain.form.domain.service;

import com.yoyomo.domain.form.domain.entity.Form;
import com.yoyomo.domain.form.domain.repository.FormRepository;
import com.yoyomo.domain.form.exception.FormNotFoundException;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FormGetService {
    private final FormRepository formRepository;

    public Form find(String formId) {
        return formRepository.findByIdAndDeletedAtIsNull(formId)
                .orElseThrow(FormNotFoundException::new);
    }

    public Optional<Form> findAsOptional(String id) {
        return formRepository.findByIdAndDeletedAtIsNull(id);
    }

    public List<Form> findAll(String clubId) {
        return formRepository.findAllByClubIdAndDeletedAtIsNullOrderByCreatedAtDesc(clubId);
    }

    public List<String> findAllIds(List<Form> forms) {
        return forms.stream()
                .map(Form::getId)
                .toList();
    }

    public List<Form> searchByKeyword(String keyword, String clubId) {
        return formRepository.findAllBySearch(clubId, keyword, keyword);
    }
}
