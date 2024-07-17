package com.yoyomo.domain.recruitment.application.usecase;

import com.yoyomo.domain.recruitment.application.dto.res.ProcessResponse;
import com.yoyomo.domain.recruitment.application.mapper.ProcessMapper;
import com.yoyomo.domain.recruitment.domain.service.RecruitmentGetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProcessUseCaseImpl implements ProcessUseCase {

    private final RecruitmentGetService recruitmentGetService;
    private final ProcessMapper mapper;

    @Override
    public List<ProcessResponse> readAll(String recruitmentId) {
        return recruitmentGetService.find(recruitmentId)
                .getProcesses().stream()
                .map(mapper::from)
                .toList();
    }
}
