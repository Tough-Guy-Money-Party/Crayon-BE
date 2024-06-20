package com.yoyomo.domain.form.application.usecase;

import com.yoyomo.domain.form.application.dto.req.FormRequest;
import com.yoyomo.domain.form.application.dto.req.FormUpdateRequest;
import com.yoyomo.domain.form.application.dto.res.FormCreateResponse;
import com.yoyomo.domain.form.application.dto.res.FormDetailsResponse;
import com.yoyomo.domain.form.application.dto.res.FormResponse;
import com.yoyomo.domain.form.application.mapper.FormMapper;
import com.yoyomo.domain.form.domain.entity.Form;
import com.yoyomo.domain.form.domain.service.FormGetService;
import com.yoyomo.domain.form.domain.service.FormSaveService;
import com.yoyomo.domain.form.domain.service.FormUpdateService;
import com.yoyomo.domain.item.application.dto.res.ItemResponse;
import com.yoyomo.domain.item.application.usecase.ItemManageUseCase;
import com.yoyomo.domain.item.domain.entity.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class FormManageUseCaseImpl implements FormManageUseCase {
    private final FormGetService formGetService;
    private final FormSaveService formSaveService;
    private final FormUpdateService formUpdateService;
    private final ItemManageUseCase itemManageUseCase;
    private final FormMapper formMapper;

    @Override
    public FormDetailsResponse read(String id) {
        Form form = formGetService.find(id);
        List<ItemResponse> itemResponses = itemManageUseCase.get(form);
        return new FormDetailsResponse(form.getTitle(), form.getDescription(), itemResponses);
    }

    @Override
    public List<FormResponse> readAll(String clubId) {
        List<Form> forms = formGetService.findAll(clubId);
        return forms.stream()
                .map(formMapper::mapToFormResponse)
                .toList();
    }

    @Override
    public FormCreateResponse create(FormRequest request) {
        List<Item> items = itemManageUseCase.create(request.itemRequests());
        Form form = formMapper.from(request, items);
        String id = formSaveService.save(form).getId();
        return new FormCreateResponse(id);
    }

    @Override
    public void update(String id, FormUpdateRequest request) {
        formUpdateService.update(id, request.title(),request.description());
        itemManageUseCase.update(id, request.itemRequests());
    }

    @Override
    public void delete(String id) {
        formUpdateService.delete(id);
    }
}
