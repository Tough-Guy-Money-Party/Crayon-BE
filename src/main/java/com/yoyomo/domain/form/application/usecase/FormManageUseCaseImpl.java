package com.yoyomo.domain.form.application.usecase;

import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.club.domain.service.ClubGetService;
import com.yoyomo.domain.form.application.dto.req.FormRequestDTO.Save;
import com.yoyomo.domain.form.application.dto.req.FormUpdateRequest;
import com.yoyomo.domain.form.application.dto.res.FormDetailsResponse;
import com.yoyomo.domain.form.application.dto.res.FormResponse;
import com.yoyomo.domain.form.application.dto.res.FormResponseDTO.SaveResponse;
import com.yoyomo.domain.form.application.mapper.FormMapper;
import com.yoyomo.domain.form.domain.entity.Form;
import com.yoyomo.domain.form.domain.service.FormSaveService;
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

    @Override
    public FormDetailsResponse read(String id) {
        return null;
    }

    @Override
    public List<FormResponse> readAll(Authentication authentication) {
        return List.of();
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
    public void update(String id, FormUpdateRequest request) {

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
