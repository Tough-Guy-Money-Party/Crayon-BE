package com.yoyomo.domain.recruitment.application.usecase;

import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.club.domain.service.ClubGetService;
import com.yoyomo.domain.club.domain.service.ClubManagerAuthService;
import com.yoyomo.domain.form.application.usecase.FormManageUseCase;
import com.yoyomo.domain.form.domain.entity.Form;
import com.yoyomo.domain.form.domain.service.FormGetService;
import com.yoyomo.domain.recruitment.application.dto.response.ProcessResponseDTO;
import com.yoyomo.domain.recruitment.application.mapper.RecruitmentMapper;
import com.yoyomo.domain.recruitment.domain.entity.Process;
import com.yoyomo.domain.recruitment.domain.entity.Recruitment;
import com.yoyomo.domain.recruitment.domain.service.RecruitmentDeleteService;
import com.yoyomo.domain.recruitment.domain.service.RecruitmentGetService;
import com.yoyomo.domain.recruitment.domain.service.RecruitmentSaveService;
import com.yoyomo.domain.recruitment.domain.service.RecruitmentUpdateService;
import com.yoyomo.domain.recruitment.exception.RecruitmentDeletedException;
import com.yoyomo.domain.user.domain.entity.Manager;
import com.yoyomo.domain.user.domain.service.UserGetService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.yoyomo.domain.form.application.dto.response.FormResponseDTO.info;
import static com.yoyomo.domain.recruitment.application.dto.request.RecruitmentRequestDTO.Save;
import static com.yoyomo.domain.recruitment.application.dto.request.RecruitmentRequestDTO.Update;
import static com.yoyomo.domain.recruitment.application.dto.response.RecruitmentResponseDTO.DetailResponse;
import static com.yoyomo.domain.recruitment.application.dto.response.RecruitmentResponseDTO.Response;

@Service
@RequiredArgsConstructor
public class RecruitmentManageUseCase {

    private final UserGetService userGetService;
    private final ClubGetService clubGetService;

    private final RecruitmentGetService recruitmentGetService;
    private final RecruitmentSaveService recruitmentSaveService;
    private final RecruitmentUpdateService recruitmentUpdateService;
    private final RecruitmentDeleteService recruitmentDeleteService;
    private final RecruitmentMapper recruitmentMapper;

    private final FormGetService formGetService;
    private final FormManageUseCase formManageUseCase;
    private final ClubManagerAuthService clubManagerAuthService;

    private final ProcessManageUseCase processManageUseCase;

    @Transactional
    public void save(Save dto, Long userId) {
        Club club = clubGetService.find(dto.clubId());
        Manager manager = userGetService.find(userId);
        clubManagerAuthService.checkAuthorization(club, manager);

        Recruitment recruitment = recruitmentSaveService.save(dto, club);
        List<Process> processes = processManageUseCase.save(dto.processes(), recruitment);
        recruitment.addProcesses(processes);
    }

    public DetailResponse read(String recruitmentId) {
        Recruitment recruitment = recruitmentGetService.find(recruitmentId);
        List<ProcessResponseDTO.Response> processes = processManageUseCase.readAll(recruitmentId);
        info form = formManageUseCase.readForm(recruitment.getFormId());

        return recruitmentMapper.toDetailResponse(recruitment, processes, form);
    }

    @Transactional(readOnly = true)
    public Page<Response> readAll(Pageable pageable, String clubId) {
        Club club = clubGetService.find(clubId);
        return recruitmentGetService.findAll(club, pageable)
                .map(Response::toResponse);
    }

    @Transactional
    public void update(String recruitmentId, Update dto, Long userId) {
        Recruitment recruitment = checkAuthorityByRecruitment(recruitmentId, userId);
        recruitment.checkModifiable();
        List<Process> processes = processManageUseCase.update(dto.processes(), recruitment);
        recruitment.addProcesses(processes);
        recruitmentUpdateService.update(recruitment, dto);
    }

    @Transactional
    public void close(String recruitmentId, Long userId) {
        Recruitment recruitment = checkAuthorityByRecruitment(recruitmentId, userId);
        recruitmentUpdateService.delete(recruitment);
    }

    @Transactional
    public void activate(String recruitmentId, String formId, Long userId) {
        checkDeletedRecruitment(recruitmentId);
        Recruitment recruitment = checkAuthorityByRecruitment(recruitmentId, userId);

        Form form = formGetService.find(formId);
        recruitmentUpdateService.update(recruitment, form.getId());
    }

    @Transactional
    public void cancel(String recruitmentId, Long userId) {
        Recruitment recruitment = checkAuthorityByRecruitment(recruitmentId, userId);
        recruitmentDeleteService.delete(recruitment);
    }

    private Recruitment checkAuthorityByRecruitment(String recruitmentId, Long userId) {
        Recruitment recruitment = recruitmentGetService.find(recruitmentId);
        Manager manager = userGetService.find(userId);
        clubManagerAuthService.checkAuthorization(recruitment.getId(), manager);

        return recruitment;
    }

    private void checkDeletedRecruitment(String recruitmentId) {
        Recruitment recruitment = recruitmentGetService.find(recruitmentId);
        if (recruitment.getDeletedAt() != null) {
            throw new RecruitmentDeletedException();
        }
    }
}
