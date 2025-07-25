package com.yoyomo.domain.form.application.usecase;

import static com.yoyomo.domain.form.application.dto.request.FormRequest.*;
import static java.util.Collections.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.club.domain.service.ClubGetService;
import com.yoyomo.domain.club.domain.service.ClubManagerAuthService;
import com.yoyomo.domain.form.application.dto.response.FormDetailResponse;
import com.yoyomo.domain.form.application.dto.response.FormResponse.DetailResponse;
import com.yoyomo.domain.form.application.dto.response.FormResponse.Info;
import com.yoyomo.domain.form.application.dto.response.FormResponse.Response;
import com.yoyomo.domain.form.application.dto.response.FormResponse.SaveResponse;
import com.yoyomo.domain.form.domain.entity.Form;
import com.yoyomo.domain.form.domain.repository.dto.LinkedRecruitment;
import com.yoyomo.domain.form.domain.service.FormGetService;
import com.yoyomo.domain.form.domain.service.FormSaveService;
import com.yoyomo.domain.form.domain.service.FormUpdateService;
import com.yoyomo.domain.item.application.dto.res.ItemResponse;
import com.yoyomo.domain.item.application.mapper.ItemResponseFactory;
import com.yoyomo.domain.item.application.usecase.ItemManageUseCase;
import com.yoyomo.domain.item.domain.entity.Item;
import com.yoyomo.domain.recruitment.domain.entity.Recruitment;
import com.yoyomo.domain.recruitment.domain.service.RecruitmentGetService;
import com.yoyomo.domain.user.domain.entity.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FormManageUseCase {

	private final FormSaveService formSaveService;
	private final ItemManageUseCase itemManageUseCase;
	private final ClubGetService clubGetService;
	private final FormGetService formGetService;
	private final FormUpdateService formUpdateService;
	private final ClubManagerAuthService clubManagerAuthService;
	private final RecruitmentGetService recruitmentGetService;
	private final ItemResponseFactory itemResponseFactory;

	@Transactional(readOnly = true)
	public DetailResponse read(String id) {
		Form form = formGetService.find(id);
		List<String> linkedRecruitmentIds = recruitmentGetService.findAllLinkedRecruitments(form.getId());

		List<ItemResponse> itemResponses = form.getItems()
			.stream()
			.map(itemResponseFactory::createItem)
			.toList();

		return DetailResponse.toResponse(form, itemResponses, linkedRecruitmentIds); // select
	}

	@Transactional(readOnly = true)
	public Info readForm(String id) {
		Optional<Form> form = formGetService.findAsOptional(id);

		return Info.toInfo(form);
	}

	@Transactional(readOnly = true)
	public List<Response> readAll(User user, String clubId) {
		checkAuthorityByClubId(user, clubId);

		List<Form> forms = formGetService.findAll(clubId);
		List<String> formIds = formGetService.findAllIds(forms);
		Map<String, List<LinkedRecruitment>> linkedRecruitments = recruitmentGetService.findAllLinkedRecruitments(
			formIds);

		return forms.stream()
			.map(form -> Response.toResponse(form, linkedRecruitments.getOrDefault(form.getId(), emptyList())))
			.toList();
	}

	@Transactional
	public SaveResponse create(Save dto, String clubId, User user) {
		checkAuthorityByClubId(user, clubId);
		List<Item> items = itemManageUseCase.create(dto.itemRequests());
		Form form = formSaveService.save(dto, items, clubId);

		return new SaveResponse(form.getId());
	}

	@Transactional
	public void update(String formId, Update dto, User user) {
		Form form = checkAuthorityByFormId(user, formId);
		formUpdateService.update(form, dto.title(), dto.description(), dto.itemRequests());
	}

	@Transactional
	public void delete(String formId, User user) {
		checkAuthorityByFormId(user, formId);
		formUpdateService.delete(formId);
	}

	@Transactional(readOnly = true)
	public List<Response> search(String keyword, String clubId, User user) {
		checkAuthorityByClubId(user, clubId);

		List<Form> forms = formGetService.searchByKeyword(keyword, clubId);
		List<String> formIds = formGetService.findAllIds(forms);
		Map<String, List<LinkedRecruitment>> linkedRecruitments = recruitmentGetService.findAllLinkedRecruitments(
			formIds);

		return forms.stream()
			.map(form -> Response.toResponse(form, linkedRecruitments.getOrDefault(form.getId(), emptyList())))
			.toList();
	}

	@Transactional(readOnly = true)
	public FormDetailResponse read(UUID recruitmentId) {
		Recruitment recruitment = recruitmentGetService.find(recruitmentId);
		Club club = recruitment.getClub();
		Form form = formGetService.find(recruitment.getFormId());

		List<ItemResponse> itemResponses = form.getItems()
			.stream()
			.map(itemResponseFactory::createItem)
			.toList();

		return FormDetailResponse.toResponse(club, recruitment, form, itemResponses);
	}

	private Form checkAuthorityByFormId(User user, String formId) {
		Form form = formGetService.find(formId);
		checkAuthorityByClubId(user, form.getClubId());

		return form;
	}

	private void checkAuthorityByClubId(User manager, String clubId) {
		Club club = clubGetService.find(clubId);
		clubManagerAuthService.checkAuthorization(club, manager);
	}

	public void replicate(String formId, User user) {
		Form form = checkAuthorityByFormId(user, formId);
		Form newForm = Form.replicate(form);
		formSaveService.save(newForm);
	}
}
