package com.yoyomo.domain.recruitment.application.usecase;

import com.yoyomo.domain.application.application.dto.response.ApplicationResponseDTO;
import com.yoyomo.domain.application.application.mapper.ApplicationMapper;
import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.club.domain.service.ClubGetService;
import com.yoyomo.domain.process.application.dto.response.ProcessResponseDTO;
import com.yoyomo.domain.process.application.mapper.ProcessMapper;
import com.yoyomo.domain.process.domain.entity.Process;
import com.yoyomo.domain.process.domain.service.ProcessSaveService;
import com.yoyomo.domain.recruitment.application.dto.request.RecruitmentRequestDTO;
import com.yoyomo.domain.recruitment.application.mapper.RecruitmentMapper;
import com.yoyomo.domain.recruitment.domain.entity.Recruitment;
import com.yoyomo.domain.recruitment.domain.repository.RecruitmentRepository;
import com.yoyomo.domain.recruitment.domain.service.RecruitmentGetService;
import com.yoyomo.domain.user.domain.entity.Manager;
import com.yoyomo.domain.user.domain.service.UserGetService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

import static com.yoyomo.domain.club.domain.entity.Club.checkAuthority;
import static com.yoyomo.domain.recruitment.application.dto.response.RecruitmentResponseDTO.Response;

@Service
@RequiredArgsConstructor
public class RecruitmentManageUseCaseImpl implements RecruitmentManageUseCase {

    private final UserGetService userGetService;
    private final ClubGetService clubGetService;
    private final ProcessSaveService processSaveService;
    private final RecruitmentMapper recruitmentMapper;
    private final RecruitmentRepository recruitmentRepository;
    private final RecruitmentGetService recruitmentGetService;
    private final ProcessMapper processMapper;
    private final ApplicationMapper applicationMapper;

    @Override @Transactional
    public void save(RecruitmentRequestDTO.Save dto, Long userId) {
        Club club = clubGetService.find(dto.clubId());
        Manager manager = userGetService.find(userId);
        checkAuthority(club, manager);
        Recruitment recruitment = recruitmentRepository.save(recruitmentMapper.from(dto, club));
        List<Process> processes = processSaveService.saveAll(dto.processes(), recruitment);
        recruitment.addProcesses(processes);
    }

    @Override
    public Response read(String recruitmentId) {
        Recruitment recruitment = recruitmentGetService.find(recruitmentId);

        List<ProcessResponseDTO.Response> processes = recruitment.getProcesses().stream()
                .map(process -> {
                    List<ApplicationResponseDTO.Response> applications = process.getApplications().stream()
                            .map(applicationMapper::toResponse)
                            .toList();

                    return processMapper.toResponse(process, applications);
                })
                .sorted(Comparator.comparingInt(ProcessResponseDTO.Response::stage))
                .toList();

        return recruitmentMapper.toResponse(recruitment, processes);
    }
}
