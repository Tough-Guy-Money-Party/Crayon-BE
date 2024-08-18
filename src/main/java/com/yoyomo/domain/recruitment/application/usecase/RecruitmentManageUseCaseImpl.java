package com.yoyomo.domain.recruitment.application.usecase;

import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.club.domain.service.ClubGetService;
import com.yoyomo.domain.process.domain.entity.Process;
import com.yoyomo.domain.process.domain.service.ProcessSaveService;
import com.yoyomo.domain.recruitment.application.dto.request.RecruitmentRequestDTO;
import com.yoyomo.domain.recruitment.application.mapper.RecruitmentMapper;
import com.yoyomo.domain.recruitment.domain.repository.RecruitmentRepository;
import com.yoyomo.domain.user.domain.entity.Manager;
import com.yoyomo.domain.user.domain.service.UserGetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.yoyomo.domain.club.domain.entity.Club.checkAuthority;

@Service
@RequiredArgsConstructor
public class RecruitmentManageUseCaseImpl implements RecruitmentManageUseCase {

    private final UserGetService userGetService;
    private final ClubGetService clubGetService;
    private final ProcessSaveService processSaveService;
    private final RecruitmentMapper recruitmentMapper;
    private final RecruitmentRepository recruitmentRepository;

    @Override
    public void save(RecruitmentRequestDTO.Save dto, Long userId) {
        Club club = clubGetService.find(dto.clubId());
        Manager manager = userGetService.find(userId);
        checkAuthority(club, manager);
        List<Process> processes = processSaveService.saveAll(dto.processes());
        recruitmentRepository.save(recruitmentMapper.from(dto, processes, club));
    }
}
