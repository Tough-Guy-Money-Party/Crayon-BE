package com.yoyomo.domain.item.domain.service;

import com.yoyomo.domain.form.domain.entity.Form;
import com.yoyomo.domain.form.domain.service.FormGetService;
import com.yoyomo.domain.form.domain.service.FormSaveService;
import com.yoyomo.domain.item.domain.entity.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemUpdateService {
    private final FormSaveService formSaveService;
    private final FormGetService formGetService;

    public void updateItem(String formId, List<Item> items) {
        Form form = formGetService.find(formId);
        form.updateItems(items);
        formSaveService.save(form);
    }
}
