package com.yoyomo.global.config.participation.service;

import com.yoyomo.domain.club.application.dto.res.ParticipationResponse;
import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.club.domain.repository.ClubRepository;
import com.yoyomo.domain.club.domain.service.ClubGetService;
import com.yoyomo.domain.club.domain.service.ClubUpdateService;
import com.yoyomo.domain.club.exception.ClubNotFoundException;
import com.yoyomo.domain.user.domain.entity.Manager;
import com.yoyomo.domain.user.domain.repository.ManagerRepository;
import com.yoyomo.domain.user.domain.service.UserGetService;
import com.yoyomo.domain.user.domain.service.UserUpdateService;
import com.yoyomo.domain.user.exception.UserNotFoundException;
import com.yoyomo.global.config.participation.exception.AlreadyParticipatedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ParticipationService {
    private final ManagerRepository managerRepository;
    private final ClubRepository clubRepository;
    private final ClubUpdateService clubUpdateService;
    private final UserUpdateService userUpdateService;
    private final UserGetService userGetService;
    private final ParticipationCodeService participationCodeService;
    private final ClubGetService clubGetService;

    public ParticipationResponse checkAndParticipate(String code, String userEmail) {
        String clubId = participationCodeService.getClubId(code);
        Manager manager = userGetService.findByEmail(userEmail);
        boolean isAlreadyParticipate = manager.getClubs()
                .stream()
                .anyMatch(club -> clubId.equals(club.getId()));
        if (isAlreadyParticipate) {
            throw new AlreadyParticipatedException();
        }
        this.addToEachList(userEmail, clubId);
        Club club = clubGetService.byId(clubId);

        return new ParticipationResponse(clubId, club.getName());
    }

    public void addToEachList(String userEmail, String clubId) {
        Manager manager = managerRepository.findByEmail(userEmail).orElseThrow(UserNotFoundException::new);
        Club club = clubRepository.findById(clubId).orElseThrow(ClubNotFoundException::new);
        clubUpdateService.addUser(manager, club);
        userUpdateService.addClub(manager, club);
    }

    public void deleteToEachList(String userId, String clubId) {
        Manager manager = managerRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Club club = clubRepository.findById(clubId).orElseThrow(ClubNotFoundException::new);
        clubUpdateService.deleteUser(manager, club);
        userUpdateService.deleteClub(manager, club);
    }

    public void addToEachList(String userEmail, Club club) {
        Manager manager = managerRepository.findByEmail(userEmail).orElseThrow(UserNotFoundException::new);
        clubUpdateService.addUser(manager, club);
        userUpdateService.addClub(manager, club);
    }
}
