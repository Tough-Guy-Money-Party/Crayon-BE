package com.yoyomo.domain.club.domain.service;

import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.club.domain.entity.ClubManager;
import com.yoyomo.domain.club.domain.repository.ClubMangerRepository;
import com.yoyomo.domain.club.exception.DuplicatedParticipationException;
import com.yoyomo.domain.user.domain.entity.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClubManagerSaveService {

    private final ClubMangerRepository clubMangerRepository;

    public ClubManager save(ClubManager clubManager) {
        return clubMangerRepository.save(clubManager);
    }

    public void saveManager(User manager, Club club) {
        List<User> managers = clubMangerRepository.findAllByClubId(club.getId())
                .stream()
                .map(ClubManager::getManager)
                .toList();

        if (managers.contains(manager)) {
            throw new DuplicatedParticipationException();
        }

        ClubManager clubManager = ClubManager.of(club, manager);
        clubMangerRepository.save(clubManager);
    }
}
