package com.yoyomo.domain.recruitment.application.usecase;

import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.club.domain.service.ClubGetService;
import com.yoyomo.domain.recruitment.application.dto.response.ProcessResponseDTO;
import com.yoyomo.domain.recruitment.application.dto.response.RecruitmentResponseDTO.DetailResponse;
import com.yoyomo.domain.recruitment.application.mapper.RecruitmentMapper;
import com.yoyomo.domain.recruitment.domain.entity.Process;
import com.yoyomo.domain.recruitment.domain.entity.Recruitment;
import com.yoyomo.domain.recruitment.domain.service.RecruitmentGetService;
import com.yoyomo.domain.recruitment.domain.service.RecruitmentSaveService;
import com.yoyomo.domain.recruitment.domain.service.RecruitmentUpdateService;
import com.yoyomo.domain.user.domain.entity.Manager;
import com.yoyomo.domain.user.domain.service.UserGetService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.yoyomo.domain.club.domain.entity.Club.checkAuthority;
import static com.yoyomo.domain.recruitment.application.dto.request.RecruitmentRequestDTO.Save;
import static com.yoyomo.domain.recruitment.application.dto.request.RecruitmentRequestDTO.Update;
import static com.yoyomo.domain.recruitment.application.dto.response.RecruitmentResponseDTO.Response;
import static com.yoyomo.domain.recruitment.domain.entity.Recruitment.checkEnabled;

@Service
@RequiredArgsConstructor
public class RecruitmentManageUseCaseImpl implements RecruitmentManageUseCase {

    private final UserGetService userGetService;
    private final ClubGetService clubGetService;
    private final RecruitmentMapper recruitmentMapper;
    private final RecruitmentGetService recruitmentGetService;
    private final RecruitmentSaveService recruitmentSaveService;
    private final RecruitmentUpdateService recruitmentUpdateService;
    private final ProcessManageUseCase processManageUseCase;

    @Override @Transactional
    public void save(Save dto, Long userId) {
        Club club = clubGetService.find(dto.clubId());
        Manager manager = userGetService.find(userId);
        checkAuthority(club, manager);
        Recruitment recruitment = recruitmentSaveService.save(dto, club);
        List<Process> processes = processManageUseCase.save(dto.processes(), recruitment);
        recruitment.addProcesses(processes);
    }

    @Override
    public DetailResponse read(String recruitmentId) {
        Recruitment recruitment = recruitmentGetService.find(recruitmentId);
        checkEnabled(recruitment);
        List<ProcessResponseDTO.Response> processes = processManageUseCase.readAll(recruitmentId);
        return recruitmentMapper.toDetailResponse(recruitment, processes);
    }

    @Override
    public Page<Response> readAll(Pageable pageable) {
        return recruitmentGetService.findAll(pageable)
                .map(recruitmentMapper::toResponse);
    }

    @Override @Transactional
    public void update(String recruitmentId, Update dto) {
        Recruitment recruitment = recruitmentGetService.find(recruitmentId);
        checkEnabled(recruitment);
        List<Process> processes = processManageUseCase.update(dto.processes(), recruitment);
        recruitment.addProcesses(processes);
        recruitmentUpdateService.update(recruitment, dto);
    }

    @Override
    public void delete(String recruitmentId) {
        Recruitment recruitment = recruitmentGetService.find(recruitmentId);
        checkEnabled(recruitment);
        recruitmentUpdateService.delete(recruitment);
    }

    @Override
    public void activate(String recruitmentId, String formId) {
        Recruitment recruitment = recruitmentGetService.find(recruitmentId);
        recruitmentUpdateService.update(recruitment, formId);
    }
}
