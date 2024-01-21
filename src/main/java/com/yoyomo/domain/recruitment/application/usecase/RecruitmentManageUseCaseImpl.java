package com.yoyomo.domain.recruitment.application.usecase;

import com.yoyomo.domain.recruitment.application.dto.req.RecruitmentRequest;
import com.yoyomo.domain.recruitment.application.mapper.RecruitmentMapper;
import com.yoyomo.domain.recruitment.domain.entity.Recruitment;
import com.yoyomo.domain.recruitment.domain.service.RecruitmentSaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecruitmentManageUseCaseImpl implements RecruitmentManageUseCase {
    private final RecruitmentSaveService recruitmentSaveService;
    private final RecruitmentMapper recruitmentMapper;

    @Override
    public void create(String formId, RecruitmentRequest request) {
        Recruitment recruitment = recruitmentMapper.from(formId, request);
        recruitmentSaveService.save(recruitment);
    }
}
