package com.yoyomo.domain.form.application.usecase;

import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.club.domain.service.ClubGetService;
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
import com.yoyomo.domain.user.domain.entity.Manager;
import com.yoyomo.domain.user.domain.service.UserGetService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class FormManageUseCaseImpl implements FormManageUseCase {
    private final FormGetService formGetService;
    private final ClubGetService clubGetService;
    private final UserGetService userGetService;
    private final FormSaveService formSaveService;
    private final FormUpdateService formUpdateService;
    private final ItemManageUseCase itemManageUseCase;
    private final FormMapper formMapper;

    @Override
    public FormDetailsResponse read(String id) {
        Form form = formGetService.find(id);
        List<ItemResponse> itemResponses = itemManageUseCase.get(form);
        return new FormDetailsResponse(form.getTitle(), form.getDescription(), itemResponses, form.isActive());
    }

    @Override
    public List<FormResponse> readAll(String clubId) {
        List<Form> forms = formGetService.findAll(clubId);
        return forms.stream()
                .map(formMapper::mapToFormResponse)
                .toList();
    }

    @Override
    public FormCreateResponse create(FormRequest request, Authentication authentication) {
        String email = authentication.getName();

        Manager manager = userGetService.findByEmail(email);
        List<Club> clubs = manager.getClubs();
        List<String> clubIds = clubGetService.extractClubIds(clubs);
        String clubId = clubIds.get(0);

        List<Item> items = itemManageUseCase.create(request.itemRequests());

        Form form = Form.builder().clubId(clubId)
                .title(request.title())
                .active(false)
                .description(request.description())
                .items(items)
                .build();

        String id = formSaveService.save(form).getId();
        return new FormCreateResponse(id);
    }

    @Override
    public void update(String id, FormUpdateRequest request) {
        formUpdateService.update(id, request.title(),request.description(), request.active());
        itemManageUseCase.update(id, request.itemRequests());
    }

    @Override
    public void delete(String id) {
        formUpdateService.delete(id);
    }
}
