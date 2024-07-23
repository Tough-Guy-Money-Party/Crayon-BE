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
    private final ClubMapper clubMapper;
    private final ManagerRepository managerRepository;
    private final ClubStyleMapper clubStyleMapper;

    @Override
    public ClubGeneralSettingResponse getGeneralSetting(Authentication authentication) {
        Manager manager = managerRepository.findByEmail(authentication.getName()).orElseThrow(UserNotFoundException::new);

        if (manager.getClubs().isEmpty()) {
            throw new ClubNotFoundException();
        }
        Club club = manager.getClubs().get(0);
        return clubMapper.clubToClubGeneralSettingResponse(club);
    }

    public void update(Authentication authentication, UpdateGeneralSettingsRequest request) {
        String email = authentication.getName();
        Manager manager = managerRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);

        if (manager.getClubs().isEmpty()) {
            throw new ClubNotFoundException();
        }
        Club club = manager.getClubs().get(0);
        clubUpdateService.from(club.getId(), request);
    }

    public void update(UpdateStyleSettingsRequest request, String userEmail) {
        Manager manager = managerRepository.findByEmail(userEmail).orElseThrow(UserNotFoundException::new);

        if (manager.getClubs().isEmpty()) {
            throw new ClubNotFoundException();
        }
        Club club = manager.getClubs().get(0);
        clubUpdateService.addStyle(club.getId(),clubStyleMapper.from(request));
    }

    public void create(Authentication authentication, String notionPageLink){
        String email = authentication.getName();
        Manager manager = managerRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);

        if (manager.getClubs().isEmpty()) {
            throw new ClubNotFoundException();
        }
        Club club = manager.getClubs().get(0);
        clubUpdateService.from(club.getId(),notionPageLink);
    }
}
