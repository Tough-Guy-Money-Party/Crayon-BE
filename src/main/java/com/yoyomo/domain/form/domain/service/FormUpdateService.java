package com.yoyomo.domain.form.domain.service;

import com.yoyomo.domain.form.domain.entity.Form;
import com.yoyomo.domain.form.domain.repository.FormRepository;
import com.yoyomo.domain.form.exception.FormUnmodifiableException;
import com.yoyomo.domain.item.application.dto.req.ItemRequest;
import com.yoyomo.domain.item.domain.entity.Item;
import com.yoyomo.domain.item.domain.service.factory.ItemFactory;
import com.yoyomo.domain.recruitment.domain.repository.RecruitmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class FormUpdateService {

    private final RecruitmentRepository recruitmentRepository;
    private final ItemFactory itemFactory;
    private final FormRepository formRepository;

    public void update(Form form, String title, String description, List<ItemRequest> rawItems) {
        long linkedRecruitmentCount = recruitmentRepository.countByFormId(form.getId());
        if (linkedRecruitmentCount != 0) {
            throw new FormUnmodifiableException(); // todo 연결이 기준인지, 활성화가 기준인지 파악 후 수정
        }

        List<Item> items = rawItems.stream()
                .map(itemFactory::createItem)
                .toList();
        form.update(title, description, items);
    }

    public void delete(String formId) {
        formRepository.deleteById(formId);
    }
}
