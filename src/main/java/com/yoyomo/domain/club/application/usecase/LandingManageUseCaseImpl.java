package com.yoyomo.domain.club.application.usecase;

import com.yoyomo.domain.club.application.dto.req.UpdateGeneralSettingsRequest;
import com.yoyomo.domain.club.application.dto.req.UpdateStyleSettingsRequest;
import com.yoyomo.domain.club.application.dto.res.ClubGeneralSettingResponse;
import com.yoyomo.domain.club.application.mapper.ClubMapper;
import com.yoyomo.domain.club.application.mapper.ClubStyleMapper;
import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.club.domain.service.ClubGetService;
import com.yoyomo.domain.club.domain.service.ClubSaveService;
import com.yoyomo.domain.club.domain.service.ClubUpdateService;
import com.yoyomo.domain.club.exception.ClubNotFoundException;
import com.yoyomo.domain.user.domain.entity.Manager;
import com.yoyomo.domain.user.domain.repository.ManagerRepository;
import com.yoyomo.domain.user.domain.service.UserGetService;
import com.yoyomo.domain.user.exception.UserNotFoundException;
import com.yoyomo.global.config.participation.service.ParticipationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class LandingManageUseCaseImpl implements LandingManageUseCase{
    private final ClubUpdateService clubUpdateService;
    private final ClubGetService clubGetService;
    private final ClubMapper clubMapper;
    private final ManagerRepository managerRepository;
    private final UserGetService userGetService;
    private final ClubStyleMapper clubStyleMapper;

    @Override
    public ClubGeneralSettingResponse getGeneralSetting(String email) {
        return clubMapper.clubToClubGeneralSettingResponse(clubGetService.byUserEmail(email));
    }

    public void update(String email, UpdateGeneralSettingsRequest request) {
        Club club = clubGetService.byUserEmail(email);
        clubUpdateService.from(club.getId(), request);
    }

    public void update(UpdateStyleSettingsRequest request, String email) {
        Club club = clubGetService.byUserEmail(email);
        clubUpdateService.addStyle(club.getId(),clubStyleMapper.from(request));
    }

    public void create(String email, String notionPageLink){
        Club club = clubGetService.byUserEmail(email);
        clubUpdateService.from(club.getId(),notionPageLink);
    }
}
