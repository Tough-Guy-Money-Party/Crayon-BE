package com.yoyomo.domain.application.application.usecase;

import com.yoyomo.domain.application.application.mapper.ApplicationMapper;
import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.application.domain.service.AnswerGetService;
import com.yoyomo.domain.application.domain.service.ApplicationGetService;
import com.yoyomo.domain.recruitment.domain.entity.Recruitment;
import com.yoyomo.domain.recruitment.domain.service.RecruitmentGetService;
import com.yoyomo.domain.user.domain.entity.Manager;
import com.yoyomo.domain.user.domain.service.UserGetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.yoyomo.domain.application.application.dto.response.ApplicationResponseDTO.Detail;
import static com.yoyomo.domain.application.application.dto.response.ApplicationResponseDTO.Response;
import static com.yoyomo.domain.club.domain.entity.Club.checkAuthority;

@Service
@RequiredArgsConstructor
public class ApplicationManageUseCaseImpl implements ApplicationManageUseCase {

    private final RecruitmentGetService recruitmentGetService;
    private final UserGetService userGetService;
    private final ApplicationGetService applicationGetService;
    private final ApplicationMapper applicationMapper;
    private final AnswerGetService answerGetService;

    @Override
    public Detail read(String applicationId, Long userId) {
        Application application = applicationGetService.find(applicationId);
        Manager manager = userGetService.find(userId);
        checkAuthority(application.getProcess().getRecruitment().getClub(), manager);

        return applicationMapper.toDetail(application, answerGetService.find(application.getAnswerId()));
    }


    @Override
    public List<Response> search(String name, String recruitmentId, Long userId) {
        checkAuthorityByRecruitmentId(recruitmentId, userId);
        Recruitment recruitment = recruitmentGetService.find(recruitmentId);

        return applicationGetService.findByName(recruitment, name).stream()
                .map(applicationMapper::toResponses)
                .toList();
    }

    private void checkAuthorityByRecruitmentId(String recruitmentId, Long userId) {
        Recruitment recruitment = recruitmentGetService.find(recruitmentId);
        Manager manager = userGetService.find(userId);
        checkAuthority(recruitment.getClub(), manager);
    }
}
