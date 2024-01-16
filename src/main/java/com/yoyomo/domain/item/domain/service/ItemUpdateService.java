package com.yoyomo.domain.item.domain.service;

import com.yoyomo.domain.form.domain.entity.Form;
import com.yoyomo.domain.form.domain.service.FormGetService;
import com.yoyomo.domain.form.domain.service.FormSaveService;
import com.yoyomo.domain.item.domain.entity.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemUpdateService {
    private final FormSaveService formSaveService;
    private final FormGetService formGetService;

    public void addItem(String formId, Item item) {
        Form form = formGetService.find(formId);
        form.addItem(item);
        formSaveService.save(form);
    }

    public void updateItem(String formId, String itemId, Item item) {
        Form form = formGetService.find(formId);
        form.updateItem(itemId, item);
        formSaveService.save(form);
    }

    public void deleteItem(String formId, String itemId) {
        Form form = formGetService.find(formId);
        form.removeItem(itemId);
        formSaveService.save(form);
    }
}
