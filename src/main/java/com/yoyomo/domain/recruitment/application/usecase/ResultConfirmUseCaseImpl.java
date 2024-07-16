package com.yoyomo.domain.recruitment.application.usecase;

import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.application.domain.service.ApplicationGetService;
import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.club.domain.service.ClubGetService;
import com.yoyomo.domain.interview.domain.entity.Interview;
import com.yoyomo.domain.recruitment.application.dto.res.ProcessResultResponse;
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

    private final ClubGetService clubGetService;
    private final RecruitmentGetService recruitmentGetService;
    private final ApplicationGetService applicationGetService;
    private final RecruitmentMapper recruitmentMapper;

    @Override
    public List<ProcessResultsResponse> read(String clubId) {
        Recruitment recruitment = recruitmentGetService.findAnnouncedRecruitment(clubId);
        return recruitment.getProcesses()
                .stream()
                .map(process -> recruitmentMapper.mapToProcessResultsResponse(recruitment.getTitle(), process))
                .toList();
    }

    @Override
    public ProcessResultResponse read(String clubId, String name, String phone, String email) {
        Club club = clubGetService.byId(clubId);
        Recruitment recruitment = recruitmentGetService.findByClubId(clubId);
        Application application = applicationGetService.find(recruitment, name, phone, email);
        if (application.hasInterview()) {
            Interview interview = application.getInterview();
            return recruitmentMapper.mapToProcessResultResponse(club.getName(), name, recruitment.getTitle(), interview);
        }
        return recruitmentMapper.mapToProcessResultResponse(club.getName(), name, recruitment.getTitle());
    }
}
