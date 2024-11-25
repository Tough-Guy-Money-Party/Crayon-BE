package com.yoyomo.domain.form.application.usecase;

import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.club.domain.service.ClubGetService;
import com.yoyomo.domain.club.domain.service.ClubManagerAuthService;
import com.yoyomo.domain.form.application.dto.request.FormRequestDTO.Save;
import com.yoyomo.domain.form.application.dto.request.FormRequestDTO.Update;
import com.yoyomo.domain.form.application.dto.response.FormResponseDTO.DetailResponse;
import com.yoyomo.domain.form.application.dto.response.FormResponseDTO.Response;
import com.yoyomo.domain.form.application.dto.response.FormResponseDTO.SaveResponse;
import com.yoyomo.domain.form.application.mapper.FormMapper;
import com.yoyomo.domain.form.domain.entity.Form;
import com.yoyomo.domain.form.domain.repository.dto.LinkedRecruitment;
import com.yoyomo.domain.form.domain.service.FormGetService;
import com.yoyomo.domain.form.domain.service.FormSaveService;
import com.yoyomo.domain.form.domain.service.FormUpdateService;
import com.yoyomo.domain.item.application.usecase.ItemManageUseCase;
import com.yoyomo.domain.item.domain.entity.Item;
import com.yoyomo.domain.recruitment.domain.service.RecruitmentGetService;
import com.yoyomo.domain.user.domain.entity.Manager;
import com.yoyomo.domain.user.domain.service.UserGetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.yoyomo.domain.form.application.dto.response.FormResponseDTO.info;
import static java.util.Collections.emptyList;

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
    private final ClubManagerAuthService clubManagerAuthService;
    private final RecruitmentGetService recruitmentGetService;

    @Override
    public DetailResponse read(String id) {
        Form form = formGetService.find(id);
        List<String> linkedRecruitmentIds = recruitmentGetService.findAllLinkedRecruitments(form.getId());

        return formMapper.toDetailResponse(form, linkedRecruitmentIds);
    }

    @Override
    public info readForm(String id) {
        Form form = formGetService.find(id);

        return formMapper.toInfo(form);
    }

    @Override
    public List<Response> readAll(Long userId, String clubId) {
        checkAuthorityByClubId(userId, clubId);

        List<Form> forms = formGetService.findAll(clubId);
        List<String> formIds = formGetService.findAllIds(forms);
        Map<String, List<LinkedRecruitment>> linkedRecruitments = recruitmentGetService.findAllLinkedRecruitments(formIds);

        return forms.stream()
                .map(form -> Response.toResponse(form, linkedRecruitments.getOrDefault(form.getId(), emptyList())))
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
        formUpdateService.update(form, dto.title(), dto.description(), dto.itemRequests());
    }

    @Override
    public void delete(String formId, Long userId) {
        checkAuthorityByFormId(userId, formId);
        formUpdateService.delete(formId);
    }

    @Override
    public List<Response> search(String keyword, String clubId, Long userId) {
        checkAuthorityByClubId(userId, clubId);

        List<Form> forms = formGetService.searchByKeyword(keyword, clubId);
        List<String> formIds = formGetService.findAllIds(forms);
        Map<String, List<LinkedRecruitment>> linkedRecruitments = recruitmentGetService.findAllLinkedRecruitments(formIds);

        return forms.stream()
                .map(form -> Response.toResponse(form, linkedRecruitments.getOrDefault(form.getId(), emptyList())))
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
        clubManagerAuthService.checkAuthorization(club, manager);
    }
}
