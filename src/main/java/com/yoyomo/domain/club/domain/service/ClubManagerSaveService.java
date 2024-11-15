package com.yoyomo.domain.club.domain.service;

import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.club.domain.entity.ClubManager;
import com.yoyomo.domain.club.domain.repository.ClubMangerRepository;
import com.yoyomo.domain.club.exception.DuplicatedParticipationException;
import com.yoyomo.domain.user.domain.entity.Manager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClubManagerSaveService {

    private final ClubMangerRepository clubMangerRepository;

    public ClubManager save(ClubManager clubManager) {
        return clubMangerRepository.save(clubManager);
    }

    public void saveManager(Manager manager, Club club) {
        List<Manager> managers = clubMangerRepository.findAllByClubId(club.getId())
                .stream()
                .map(ClubManager::getManager)
                .toList();

        if (managers.contains(manager)) {
            throw new DuplicatedParticipationException();
        }

        ClubManager clubManager = new ClubManager(manager, club);
        clubMangerRepository.save(clubManager);
    }
}
