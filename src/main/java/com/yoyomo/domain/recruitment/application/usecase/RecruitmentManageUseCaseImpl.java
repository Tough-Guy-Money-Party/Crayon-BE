package com.yoyomo.domain.recruitment.application.usecase;

import com.yoyomo.domain.form.domain.entity.Form;
import com.yoyomo.domain.form.domain.service.FormGetService;
import com.yoyomo.domain.recruitment.application.dto.req.RecruitmentRequest;
import com.yoyomo.domain.recruitment.application.dto.res.RecruitmentDetailsResponse;
import com.yoyomo.domain.recruitment.application.dto.res.RecruitmentResponse;
import com.yoyomo.domain.recruitment.application.mapper.RecruitmentMapper;
import com.yoyomo.domain.recruitment.domain.entity.Recruitment;
import com.yoyomo.domain.recruitment.domain.service.RecruitmentGetService;
import com.yoyomo.domain.recruitment.domain.service.RecruitmentSaveService;
import com.yoyomo.domain.recruitment.domain.service.RecruitmentUpdateService;
import lombok.RequiredArgsConstructor;
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

    @Override
    public void create(RecruitmentRequest request) {
        Form form = formGetService.find(request.formId());
        Recruitment recruitment = recruitmentMapper.from(request, form);
        recruitmentSaveService.save(recruitment);
    }

    @Override
    public RecruitmentDetailsResponse read(String recruitmentId) {
        Recruitment recruitment = recruitmentGetService.find(recruitmentId);
        return recruitmentMapper.mapToRecruitmentDetails(recruitment);
    }

    @Override
    public List<RecruitmentResponse> readAll(String clubId) {
        List<Recruitment> recruitments = recruitmentGetService.findAll(clubId);
        return recruitments.stream()
                .map(recruitmentMapper::mapToRecruitmentResponse)
                .toList();
    }

    @Override
    public void update(String recruitmentId, RecruitmentRequest request) {
        recruitmentUpdateService.from(recruitmentId, request);
    }

    @Override
    public void delete(String recruitmentId) {
        recruitmentUpdateService.delete(recruitmentId);
    }
}
