package com.yoyomo.domain.recruitment.application.usecase;

import com.yoyomo.domain.recruitment.application.dto.res.ProcessResultsResponse;
import com.yoyomo.domain.recruitment.application.mapper.RecruitmentMapper;
import com.yoyomo.domain.recruitment.domain.entity.Recruitment;
import com.yoyomo.domain.recruitment.domain.service.RecruitmentGetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResultConfirmUseCaseImpl implements ResultConfirmUseCase {

    private final RecruitmentGetService recruitmentGetService;
    private final RecruitmentMapper recruitmentMapper;

    @Override
    public List<ProcessResultsResponse> read(String clubId) {
        Recruitment recruitment = recruitmentGetService.findAnnouncedRecruitment(clubId);
        return recruitment.getProcesses()
                .stream()
                .map(process -> recruitmentMapper.mapToProcessResultsResponse(recruitment.getTitle(), process))
                .toList();
    }
}
