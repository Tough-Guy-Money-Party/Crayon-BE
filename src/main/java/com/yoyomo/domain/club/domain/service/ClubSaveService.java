package com.yoyomo.domain.club.domain.service;

import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.club.domain.entity.ClubManager;
import com.yoyomo.domain.club.domain.repository.ClubMangerRepository;
import com.yoyomo.domain.club.domain.repository.ClubRepository;
import com.yoyomo.domain.landing.domain.entity.Landing;
import com.yoyomo.domain.landing.domain.repository.LandingRepository;
import com.yoyomo.domain.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClubSaveService {

    private final ClubRepository clubRepository;
    private final ClubMangerRepository clubMangerRepository;
    private final LandingRepository landingRepository;

    public Club save(Club club, User manager) {
        ClubManager clubManager = ClubManager.of(club, manager);
        clubMangerRepository.save(clubManager);
        landingRepository.save(new Landing(club));
        return clubRepository.save(club);
    }
}
