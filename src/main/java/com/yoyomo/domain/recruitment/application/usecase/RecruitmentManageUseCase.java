package com.yoyomo.domain.recruitment.application.usecase;

import com.yoyomo.domain.application.domain.service.ApplicationDeleteService;
import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.club.domain.service.ClubGetService;
import com.yoyomo.domain.club.domain.service.ClubManagerAuthService;
import com.yoyomo.domain.form.application.usecase.FormManageUseCase;
import com.yoyomo.domain.form.domain.entity.Form;
import com.yoyomo.domain.form.domain.service.FormGetService;
import com.yoyomo.domain.recruitment.application.dto.request.RecruitmentUpdateRequest;
import com.yoyomo.domain.recruitment.application.dto.response.ProcessResponseDTO;
import com.yoyomo.domain.recruitment.domain.entity.Process;
import com.yoyomo.domain.recruitment.domain.entity.Recruitment;
import com.yoyomo.domain.recruitment.domain.service.ProcessDeleteService;
import com.yoyomo.domain.recruitment.domain.service.RecruitmentDeleteService;
import com.yoyomo.domain.recruitment.domain.service.RecruitmentGetService;
import com.yoyomo.domain.recruitment.domain.service.RecruitmentSaveService;
import com.yoyomo.domain.recruitment.domain.service.RecruitmentUpdateService;
import com.yoyomo.domain.recruitment.exception.RecruitmentDeletedException;
import com.yoyomo.domain.user.domain.entity.User;
import com.yoyomo.domain.user.domain.service.UserGetService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static com.yoyomo.domain.form.application.dto.response.FormResponseDTO.Info;
import static com.yoyomo.domain.recruitment.application.dto.request.RecruitmentRequestDTO.Save;
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

    private final FormGetService formGetService;
    private final FormManageUseCase formManageUseCase;
    private final ClubManagerAuthService clubManagerAuthService;

    private final ProcessManageUseCase processManageUseCase;
    private final ProcessDeleteService processDeleteService;

    private final ApplicationDeleteService applicationDeleteService;

    @Transactional
    public void save(Save dto, Long userId) {
        Club club = clubGetService.find(dto.clubId());
        User manager = userGetService.find(userId);
        clubManagerAuthService.checkAuthorization(club, manager);

        Recruitment recruitment = recruitmentSaveService.save(dto, club);
        List<Process> processes = processManageUseCase.save(dto.processes(), recruitment);
        recruitment.addProcesses(processes);
    }

    public DetailResponse read(UUID recruitmentId, long userId) {
        Recruitment recruitment = recruitmentGetService.find(recruitmentId);
        List<ProcessResponseDTO.Response> processes = processManageUseCase.readAll(recruitmentId, userId);
        Info form = formManageUseCase.readForm(recruitment.getFormId());

        return DetailResponse.toDetailResponse(recruitment, processes, form);
    }

    @Transactional(readOnly = true)
    public Page<Response> readAll(Pageable pageable, String clubId) {
        Club club = clubGetService.find(clubId);
        return recruitmentGetService.findAll(club, pageable)
                .map(Response::toResponse);
    }

    @Transactional
    public void update(String recruitmentId, RecruitmentUpdateRequest request, long userId) {
        Recruitment recruitment = checkAuthorityByRecruitment(recruitmentId, userId);
        recruitment.checkModifiable();
        recruitment.update(request.title(), request.position(), request.startAt(), request.endAt());
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

        applicationDeleteService.deleteByRecruitmentId(recruitment);
        processDeleteService.deleteAllByRecruitment(recruitment);
        recruitmentDeleteService.delete(recruitment);
    }

    private Recruitment checkAuthorityByRecruitment(String recruitmentId, Long userId) {
        Recruitment recruitment = recruitmentGetService.find(recruitmentId);
        User manager = userGetService.find(userId);
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
