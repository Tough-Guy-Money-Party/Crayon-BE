package com.yoyomo.domain.club.application.usecase;

import com.yoyomo.domain.club.application.dto.request.ClubRequestDTO;
import com.yoyomo.domain.club.application.mapper.ClubMapper;
import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.club.domain.entity.ClubManager;
import com.yoyomo.domain.club.domain.service.*;
import com.yoyomo.domain.club.exception.DuplicatedSubDomainException;
import com.yoyomo.domain.user.application.dto.response.ManagerResponseDTO;
import com.yoyomo.domain.user.application.mapper.ManagerMapper;
import com.yoyomo.domain.user.domain.entity.Manager;
import com.yoyomo.domain.user.domain.service.UserGetService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.yoyomo.domain.club.application.dto.request.ClubRequestDTO.Save;
import static com.yoyomo.domain.club.application.dto.response.ClubResponseDTO.*;
import static com.yoyomo.domain.club.domain.entity.Club.checkAuthority;
import static com.yoyomo.domain.club.domain.entity.Club.checkDuplicateParticipate;

@Service
@RequiredArgsConstructor
public class ClubManageUseCaseImpl implements ClubManageUseCase {

    private final UserGetService userGetService;
    private final ClubSaveService clubSaveService;
    private final ClubManagerSaveService clubManagerSaveService;
    private final ClubMapper clubMapper;
    private final ClubGetService clubGetService;
    private final ClubUpdateService clubUpdateService;
    private final ManagerMapper managerMapper;
    private final ClubManagerGetService clubManagerGetService;
    private final ClubManagerDeleteService clubManagerDeleteService;

    @Override @Transactional
    public Response save(Save dto, Long userId) {
        checkDuplicatedSubDomain(dto.subDomain());
        Manager manager = userGetService.find(userId);
        Club club = clubSaveService.save(clubMapper.from(dto));
        mapFK(manager, club);

        return clubMapper.toResponse(club);
    }

    @Override
    public Response read(String clubId) {
        Club club = clubGetService.find(clubId);
        return clubMapper.toResponse(club);
    }

    @Override
    public void update(String clubId, Save dto) {
        checkDuplicatedSubDomain(dto.subDomain());
        Club club = clubGetService.find(clubId);
        clubUpdateService.update(club, dto);
    }

    @Override
    public void delete(String clubId) {
        Club club = clubGetService.find(clubId);
        clubUpdateService.delete(club);
    }

    @Override
    public List<ManagerResponseDTO.ManagerInfo> getManagers(String clubId) {
        Club club = clubGetService.find(clubId);

        return club.getClubManagers().stream()
                .map(ClubManager::getManager)
                .map(managerMapper::toManagerInfoDTO)
                .toList();
    }

    @Override @Transactional
    public Participation participate(ClubRequestDTO.Participation dto, Long userId) {
        Club club = clubGetService.findByCode(dto.code());
        Manager manager = userGetService.find(userId);

        checkDuplicateParticipate(club, manager);
        mapFK(manager, club);

        return clubMapper.toParticipation(club);
    }

    @Override
    public Code readCode(String clubId, Long userId) {
        Club club = clubGetService.find(clubId);
        Manager manager = userGetService.find(userId);
        checkAuthority(club, manager);
        return new Code(club.getCode());
    }

    @Override
    public Code updateCode(String clubId, Long userId) {
        Club club = clubGetService.find(clubId);
        Manager manager = userGetService.find(userId);
        checkAuthority(club, manager);
        return new Code(clubUpdateService.update(club));
    }

    @Override
    public void deleteManagers(ClubRequestDTO.Delete dto) {
        Club club = clubGetService.find(dto.clubId());
        for (Long userId : dto.userIds()) {
            Manager manager = userGetService.find(userId);
            ClubManager clubManager = clubManagerGetService.find(club, manager);
            clubManagerDeleteService.delete(clubManager);
        }
    }

    private void checkDuplicatedSubDomain(String subDomain) {
        if(clubGetService.checkSubDomain(subDomain))
            throw new DuplicatedSubDomainException();
    }

    private void mapFK(Manager manager, Club club) {
        ClubManager clubManager = clubManagerSaveService.save(manager, club);
        manager.addClubManager(clubManager);
        club.addClubManager(clubManager);
    }
}
