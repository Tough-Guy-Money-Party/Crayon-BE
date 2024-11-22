package com.yoyomo.domain.form.domain.service;

import com.yoyomo.domain.form.domain.entity.Form;
import com.yoyomo.domain.form.domain.repository.FormRepository;
import com.yoyomo.domain.form.exception.FormNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FormGetService {
    private final FormRepository formRepository;

    public Form find(String id) {
        return formRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(FormNotFoundException::new);
    }

    public List<Form> findAll(String clubId) {
        return formRepository.findAllByClubIdAndDeletedAtIsNull(clubId);
    }

    public List<String> findAllIds(List<Form> forms) {
        return forms.stream()
                .map(Form::getId)
                .toList();
    }

    public List<Form> searchByKeyword(String keyword, String clubId) {
        return formRepository.findByClubIdAndTitleRegexOrClubIdAndDescriptionRegex(clubId, keyword, clubId, keyword);
    }
}
