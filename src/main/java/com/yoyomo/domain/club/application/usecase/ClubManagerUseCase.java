package com.yoyomo.domain.club.application.usecase;

import com.yoyomo.domain.club.application.dto.request.ClubManagerUpdateDto;
import com.yoyomo.domain.club.application.dto.request.ClubRequestDTO;
import com.yoyomo.domain.club.application.dto.response.ClubResponseDTO;
import com.yoyomo.domain.club.application.mapper.ClubMapperImpl;
import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.club.domain.entity.ClubManager;
import com.yoyomo.domain.club.domain.service.ClubGetService;
import com.yoyomo.domain.club.domain.service.ClubManagerDeleteService;
import com.yoyomo.domain.club.domain.service.ClubManagerGetService;
import com.yoyomo.domain.club.domain.service.ClubManagerSaveService;
import com.yoyomo.domain.club.domain.service.ClubUpdateService;
import com.yoyomo.domain.club.domain.service.ClubValidateService;
import com.yoyomo.domain.user.application.dto.response.UserResponseDTO;
import com.yoyomo.domain.user.application.mapper.ManagerMapper;
import com.yoyomo.domain.user.domain.entity.User;
import com.yoyomo.domain.user.domain.service.UserGetService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClubManagerUseCase {

    private final UserGetService userGetService;
    private final ClubGetService clubGetService;
    private final ClubUpdateService clubUpdateService;
    private final ClubValidateService clubValidateService;
    private final ClubMapperImpl clubMapper;
    private final ClubManagerSaveService clubManagerSaveService;
    private final ClubManagerDeleteService clubManagerDeleteService;
    private final ClubManagerGetService clubManagerGetService;
    private final ManagerMapper managerMapper;

    public List<UserResponseDTO.ManagerInfo> getManagers(UUID clubId, Long userId) {
        clubValidateService.checkAuthority(clubId, userId);
        List<ClubManager> clubManagers = clubManagerGetService.readAllManagers(clubId);

        return clubManagers.stream()
                .map(ClubManager::getManager)
                .map(managerMapper::toManagerInfoDTO)
                .toList();
    }

    @Transactional
    public ClubResponseDTO.Participation participate(ClubRequestDTO.Participation dto, Long userId) {
        Club club = clubGetService.findByCode(dto.code());
        User user = userGetService.find(userId);

        clubManagerSaveService.saveManager(user, club);
        return clubMapper.toParticipation(club);
    }

    public ClubResponseDTO.Code readCode(String clubId, Long userId) {
        Club club = clubValidateService.checkAuthority(clubId, userId);

        return new ClubResponseDTO.Code(club.getCode());
    }

    @Transactional
    public ClubResponseDTO.Code updateCode(String clubId, Long userId) {
        Club club = clubValidateService.checkAuthority(clubId, userId);

        String updatedCode = clubUpdateService.update(club);
        return new ClubResponseDTO.Code(updatedCode);
    }

    @Transactional
    public void deleteManagers(ClubRequestDTO.Delete dto, Long userId) {
        clubValidateService.checkOwnerAuthority(dto.clubId(), userId);

        clubManagerDeleteService.delete(dto.clubId(), dto.userIds(), userId);
    }

    @Transactional
    public void updateOwner(ClubManagerUpdateDto dto, Long userId, UUID clubId) {
        Club club = clubValidateService.checkOwnerAuthority(clubId, userId);

        ClubManager owner = clubManagerGetService.findByUserId(club, userId);
        ClubManager manager = clubManagerGetService.findByUserId(club, dto.userId());

        manager.toOwner();
        owner.toManager();
    }
}
