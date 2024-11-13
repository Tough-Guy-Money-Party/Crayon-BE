package com.yoyomo.domain.club.domain.service;

import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.club.domain.entity.ClubManager;
import com.yoyomo.domain.club.domain.repository.ClubMangerRepository;
import com.yoyomo.domain.user.domain.entity.Manager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClubManagerSaveService {

    private final ClubMangerRepository clubMangerRepository;

    public ClubManager save(ClubManager clubManager) {
        return clubMangerRepository.save(clubManager);
    }

    public void saveManager(Manager manager, Club club) {
        ClubManager clubManager = new ClubManager(manager, club);
        clubMangerRepository.save(clubManager);
    }
}
