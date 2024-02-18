package com.yoyomo.global.config.participation.service;

import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.club.domain.repository.ClubRepository;
import com.yoyomo.domain.club.domain.service.ClubUpdateService;
import com.yoyomo.domain.user.domain.entity.Manager;
import com.yoyomo.domain.user.domain.repository.ManagerRepository;
import com.yoyomo.domain.user.domain.service.UserGetService;
import com.yoyomo.domain.user.domain.service.UserUpdateService;
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
    public void checkAndParticipate (String code, String userEmail) {
        String clubId = participationCodeService.getClubId(code);
        Manager manager = userGetService.findByEmail(userEmail);
        boolean isAlreadyParticipate = manager.getClubs()
                .stream()
                .anyMatch(club -> clubId.equals(club.getId()));
        if (isAlreadyParticipate){
            throw new AlreadyParticipatedException();
        }
        this.addToEachList(userEmail, clubId);
    }

    public void addToEachList (String userEmail, String clubId) {
        Manager manager = managerRepository.findByEmail(userEmail).get();
        Club club = clubRepository.findById(clubId).get();
        clubUpdateService.addUser(manager, club);
        userUpdateService.addClub(manager, club);
    }

    public void addToEachList (String userEmail, Club club) {
        Manager manager = managerRepository.findByEmail(userEmail).get();
        clubUpdateService.addUser(manager, club);
        userUpdateService.addClub(manager, club);
    }
}
