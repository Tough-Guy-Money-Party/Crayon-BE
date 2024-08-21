package com.yoyomo.domain.form.application.usecase;

import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.club.domain.service.ClubGetService;
import com.yoyomo.domain.form.application.dto.request.FormRequestDTO.Save;
import com.yoyomo.domain.form.application.dto.request.FormRequestDTO.Update;
import com.yoyomo.domain.form.application.dto.response.FormResponse;
import com.yoyomo.domain.form.application.dto.response.FormResponseDTO.DetailResponse;
import com.yoyomo.domain.form.application.dto.response.FormResponseDTO.Response;
import com.yoyomo.domain.form.application.dto.response.FormResponseDTO.SaveResponse;
import com.yoyomo.domain.form.application.mapper.FormMapper;
import com.yoyomo.domain.form.domain.entity.Form;
import com.yoyomo.domain.form.domain.service.FormGetService;
import com.yoyomo.domain.form.domain.service.FormSaveService;
import com.yoyomo.domain.form.domain.service.FormUpdateService;
import com.yoyomo.domain.item.application.usecase.ItemManageUseCase;
import com.yoyomo.domain.item.domain.entity.Item;
import com.yoyomo.domain.user.domain.entity.Manager;
import com.yoyomo.domain.user.domain.service.UserGetService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.yoyomo.domain.club.domain.entity.Club.checkAuthority;

@Service
@RequiredArgsConstructor
public class FormManageUseCaseImpl implements FormManageUseCase {

    private final FormSaveService formSaveService;
    private final ItemManageUseCase itemManageUseCase;
    private final FormMapper formMapper;
    private final ClubGetService clubGetService;
    private final UserGetService userGetService;
    private final FormGetService formGetService;
    private final FormUpdateService formUpdateService;

    @Override
    public DetailResponse read(String id) {
        Form form = formGetService.find(id);
        return formMapper.toDetailResponse(form);
    }

    @Override
    public List<Response> readAll(Long userId, String clubId) {
        Manager manager = userGetService.find(userId);
        Club club = clubGetService.find(clubId);
        checkAuthority(club, manager);

        return formGetService.findAll(clubId).stream()
                .map(formMapper::toResponse)
                .toList();
    }

    @Override
    public SaveResponse create(Save dto, String clubId, Long userId) {
        Club club = clubGetService.find(clubId);
        Manager manager = userGetService.find(userId);
        checkAuthority(club, manager);

        List<Item> items = itemManageUseCase.create(dto.itemRequests());
        Form form = formSaveService.save(formMapper.from(dto, items, clubId));
        return new SaveResponse(form.getId());
    }

    @Override
    public void update(String id, Update dto) {
        formUpdateService.update(id, dto);
        itemManageUseCase.update(id, dto.itemRequests());
    }

    @Override
    public void update(String id, Boolean enabled) {

    }

    @Override
    public void delete(String formId) {

    }

    @Override
    public List<FormResponse> searchByKeyword(String keyword, Authentication authentication) {
        return List.of();
    }
}
