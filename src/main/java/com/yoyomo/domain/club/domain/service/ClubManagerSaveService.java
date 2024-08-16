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

    public ClubManager save(Manager manager, Club club) {
        return clubMangerRepository.save(new ClubManager(manager, club));
    }
}
