package com.yoyomo.domain.recruitment.application.usecase;

import com.yoyomo.domain.application.domain.service.ApplicationGetService;
import com.yoyomo.domain.form.application.dto.req.FormUpdateRequest;
import com.yoyomo.domain.form.application.mapper.FormMapper;
import com.yoyomo.domain.form.domain.entity.Form;
import com.yoyomo.domain.form.domain.service.FormGetService;
import com.yoyomo.domain.item.application.usecase.ItemManageUseCase;
import com.yoyomo.domain.item.domain.entity.Item;
import com.yoyomo.domain.recruitment.application.dto.req.RecruitmentModifyRequest;
import com.yoyomo.domain.recruitment.application.dto.req.RecruitmentRequest;
import com.yoyomo.domain.recruitment.application.dto.res.RecruitmentDetailsResponse;
import com.yoyomo.domain.recruitment.application.dto.res.RecruitmentResponse;
import com.yoyomo.domain.recruitment.application.mapper.RecruitmentMapper;
import com.yoyomo.domain.recruitment.domain.entity.Recruitment;
import com.yoyomo.domain.recruitment.domain.service.RecruitmentGetService;
import com.yoyomo.domain.recruitment.domain.service.RecruitmentSaveService;
import com.yoyomo.domain.recruitment.domain.service.RecruitmentUpdateService;
import com.yoyomo.domain.user.domain.entity.Manager;
import com.yoyomo.domain.user.domain.service.UserGetService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecruitmentManageUseCaseImpl implements RecruitmentManageUseCase {
    private final RecruitmentSaveService recruitmentSaveService;
    private final RecruitmentGetService recruitmentGetService;
    private final RecruitmentUpdateService recruitmentUpdateService;
    private final RecruitmentMapper recruitmentMapper;
    private final FormGetService formGetService;
    private final FormMapper formMapper;
    private final ApplicationGetService applicationGetService;
    private final ItemManageUseCase itemManageUseCase;
    private final UserGetService userGetService;

    @Override
    public void create(RecruitmentRequest request, Authentication authentication) {
        String email = authentication.getName();
        Manager manager = userGetService.findByEmail(email);
        String clubId = manager.getClubs().get(0).getId();
        Recruitment recruitment = recruitmentMapper.from(request, clubId);
        recruitmentSaveService.save(recruitment);
    }

    @Override
    public RecruitmentDetailsResponse read(String recruitmentId) {
        Recruitment recruitment = recruitmentGetService.find(recruitmentId);
        return recruitmentMapper.mapToRecruitmentDetails(recruitment);
    }

    @Override
    public Page<RecruitmentResponse> readAll(String clubId, PageRequest pageRequest) {
        Page<Recruitment> recruitments = recruitmentGetService.findAll(clubId, pageRequest);

        return recruitments.map(recruitment ->
                recruitmentMapper.mapToRecruitmentResponse(recruitment, applicationGetService, recruitmentGetService)
        );
    }

    @Override
    public void update(String recruitmentId, RecruitmentModifyRequest request) {
        if (request.formId() != null) {
            Form form = formGetService.find(request.formId());
            recruitmentUpdateService.from(recruitmentId, form);
        }
        recruitmentUpdateService.from(recruitmentId, request);
    }

    @Override
    public void update(String recruitmentId, FormUpdateRequest request) {
        List<Item> items = itemManageUseCase.create(request.itemRequests());
        Form form = formMapper.from(request, items);
        recruitmentUpdateService.from(recruitmentId, form);
    }

    @Override
    public void delete(String recruitmentId) {
        recruitmentUpdateService.delete(recruitmentId);
    }
}
