package com.yoyomo.domain.club.domain.service;

import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.club.domain.entity.ClubManager;
import com.yoyomo.domain.club.domain.repository.ClubMangerRepository;
import com.yoyomo.domain.club.domain.repository.ClubRepository;
import com.yoyomo.domain.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClubSaveService {

    private final ClubRepository clubRepository;
    private final ClubMangerRepository clubMangerRepository;

    public Club save(Club club, User manager) {
        ClubManager clubManager = ClubManager.asOwner(club, manager);
        clubMangerRepository.save(clubManager);
        return clubRepository.save(club);
    }
}
