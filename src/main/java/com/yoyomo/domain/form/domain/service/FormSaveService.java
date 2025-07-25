package com.yoyomo.domain.form.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.yoyomo.domain.form.application.dto.request.FormRequest.Save;
import com.yoyomo.domain.form.application.mapper.FormMapper;
import com.yoyomo.domain.form.domain.entity.Form;
import com.yoyomo.domain.form.domain.repository.FormRepository;
import com.yoyomo.domain.item.domain.entity.Item;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FormSaveService {
	private final FormRepository formRepository;
	private final FormMapper formMapper;

	public Form save(Form form) {
		return formRepository.save(form);
	}

	public Form save(Save dto, List<Item> items, String clubId) {
		return formRepository.save(formMapper.from(dto, items, clubId));
	}
}

