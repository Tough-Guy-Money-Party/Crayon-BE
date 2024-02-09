package com.yoyomo.global.config.participation.service;

import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.club.domain.service.ClubGetService;
import com.yoyomo.domain.club.domain.service.ClubUpdateService;
import com.yoyomo.global.config.participation.exception.AlreadyParticipatedException;
import com.yoyomo.global.config.participation.exception.InvalidPaticipationCodeException;
import com.yoyomo.domain.user.domain.entity.User;
import com.yoyomo.domain.user.domain.service.UserGetService;
import com.yoyomo.domain.user.domain.service.UserUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ParticipationService {
    private final ClubUpdateService clubUpdateService;
    private final UserUpdateService userUpdateService;
    private final UserGetService userGetService;
    private final ParticipationCodeService participationCodeService;
    public void checkAndParticipate (String code, String userEmail) {
        String clubId = participationCodeService.getClubId(code);
        User user = userGetService.findByEmail(userEmail);
        boolean isAlreadyParticipate = user.getClubs()
                .stream()
                .anyMatch(club -> clubId.equals(club.getId()));
        if (!isAlreadyParticipate){
            this.addToEachList(userEmail, clubId);
            return;
        }
        throw new AlreadyParticipatedException();
    }

    public void addToEachList (String userEmail, String clubId) {
        clubUpdateService.addUser(userEmail, clubId);
        userUpdateService.addClub(userEmail, clubId);
    }
}
