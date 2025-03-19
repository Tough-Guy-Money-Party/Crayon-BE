package com.yoyomo.domain.club.domain.service;

import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.club.domain.entity.ClubManager;
import com.yoyomo.domain.club.domain.repository.ClubMangerRepository;
import com.yoyomo.domain.club.exception.DuplicatedParticipationException;
import com.yoyomo.domain.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClubManagerSaveService {

    private final ClubMangerRepository clubMangerRepository;

    public ClubManager saveManager(User manager, Club club) {
        List<User> managers = clubMangerRepository.findAllByClubId(club.getId())
                .stream()
                .map(ClubManager::getManager)
                .toList();

        if (managers.contains(manager)) {
            throw new DuplicatedParticipationException();
        }

        ClubManager clubManager = ClubManager.asManager(club, manager);
        return clubMangerRepository.save(clubManager);
    }
}
