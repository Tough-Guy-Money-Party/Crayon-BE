package com.yoyomo.domain.form.application.usecase;

import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.club.domain.service.ClubGetService;
import com.yoyomo.domain.form.application.dto.request.FormRequestDTO.Save;
import com.yoyomo.domain.form.application.dto.request.FormRequestDTO.Update;
import com.yoyomo.domain.form.application.dto.response.FormResponseDTO.DetailResponse;
import com.yoyomo.domain.form.application.dto.response.FormResponseDTO.Response;
import com.yoyomo.domain.form.application.dto.response.FormResponseDTO.SaveResponse;
import com.yoyomo.domain.form.application.mapper.FormMapper;
import com.yoyomo.domain.form.domain.entity.Form;
import com.yoyomo.domain.form.domain.service.FormGetService;
import com.yoyomo.domain.form.domain.service.FormSaveService;
import com.yoyomo.domain.form.domain.service.FormUpdateService;
import com.yoyomo.domain.form.exception.FormUnmodifiableException;
import com.yoyomo.domain.item.application.usecase.ItemManageUseCase;
import com.yoyomo.domain.item.domain.entity.Item;
import com.yoyomo.domain.user.domain.entity.Manager;
import com.yoyomo.domain.user.domain.service.UserGetService;
import lombok.RequiredArgsConstructor;
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
        checkAuthorityByClubId(userId, clubId);

        return formGetService.findAll(clubId).stream()
                .map(formMapper::toResponse)
                .toList();
    }

    @Override
    public SaveResponse create(Save dto, String clubId, Long userId) {
        checkAuthorityByClubId(userId, clubId);
        List<Item> items = itemManageUseCase.create(dto.itemRequests());
        Form form = formSaveService.save(dto, items, clubId);

        return new SaveResponse(form.getId());
    }

    @Override
    public void update(String formId, Update dto, Long userId) {
        Form form = checkAuthorityByFormId(userId, formId);

        if(form.getRecruitmentIds() != null)
            throw new FormUnmodifiableException();

        formUpdateService.update(formId, dto);
        itemManageUseCase.update(formId, dto.itemRequests());
    }

    @Override
    public void delete(String formId, Long userId) {
        checkAuthorityByFormId(userId, formId);
        formUpdateService.delete(formId);
    }

    @Override
    public List<Response> search(String keyword, String clubId, Long userId) {
        checkAuthorityByClubId(userId, clubId);

        return formGetService.searchByKeyword(keyword, clubId).stream()
                .filter(form -> form.getDeletedAt() == null)    // JPA 메서드에서 해결하려 해봤지만 안돼서 스트림으로 해결
                .map(formMapper::toResponse)
                .toList();
    }

    private Form checkAuthorityByFormId(Long userId, String formId) {
        Form form = formGetService.find(formId);
        checkAuthorityByClubId(userId, form.getClubId());

        return form;
    }

    private void checkAuthorityByClubId(Long userId, String clubId) {
        Manager manager = userGetService.find(userId);
        Club club = clubGetService.find(clubId);
        checkAuthority(club, manager);
    }
}
