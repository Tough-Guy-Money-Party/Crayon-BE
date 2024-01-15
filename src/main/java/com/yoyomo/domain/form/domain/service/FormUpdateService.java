package com.yoyomo.domain.form.domain.service;

import com.yoyomo.domain.form.domain.entity.Form;
import com.yoyomo.domain.item.domain.entity.Item;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class FormUpdateService {
    private final FormSaveService formSaveService;
    private final FormGetService formGetService;

    public void addItem(String formId, Item item) {
        Form form = formGetService.byId(formId);
        form.addItem(item);
        formSaveService.save(form);
    }

    public void updateItem(String formId, String itemId, Item item) {
        Form form = formGetService.byId(formId);
        form.removeItem(itemId);
        form.addItem(item);
        formSaveService.save(form);
    }
}
