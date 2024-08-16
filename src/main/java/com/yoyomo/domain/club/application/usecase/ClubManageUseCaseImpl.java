package com.yoyomo.domain.club.application.usecase;

import com.yoyomo.domain.club.application.mapper.ClubMapper;
import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.club.domain.entity.ClubManager;
import com.yoyomo.domain.club.domain.service.ClubGetService;
import com.yoyomo.domain.club.domain.service.ClubManagerSaveService;
import com.yoyomo.domain.club.domain.service.ClubSaveService;
import com.yoyomo.domain.club.domain.service.ClubUpdateService;
import com.yoyomo.domain.user.domain.entity.Manager;
import com.yoyomo.domain.user.domain.service.UserGetService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.yoyomo.domain.club.application.dto.request.ClubRequestDTO.Save;
import static com.yoyomo.domain.club.application.dto.response.ClubResponseDTO.Response;

@Service
@RequiredArgsConstructor
public class ClubManageUseCaseImpl implements ClubManageUseCase {

    private final UserGetService userGetService;
    private final ClubSaveService clubSaveService;
    private final ClubManagerSaveService clubManagerSaveService;
    private final ClubMapper clubMapper;
    private final ClubGetService clubGetService;
    private final ClubUpdateService clubUpdateService;

    @Override @Transactional
    public Response save(Save dto, Long userId) {
        Manager manager = userGetService.findById(userId);
        Club club = clubSaveService.save(clubMapper.from(dto));
        ClubManager clubManager = clubManagerSaveService.save(manager, club);
        manager.addClubManager(clubManager);
        club.addClubManager(clubManager);

        return clubMapper.to(club);
    }

    @Override
    public Response read(String clubId) {
        Club club = clubGetService.find(clubId);
        return clubMapper.to(club);
    }

    @Override
    public void update(String clubId, Save dto) {
        Club club = clubGetService.find(clubId);
        clubUpdateService.update(club, dto);
    }
}
