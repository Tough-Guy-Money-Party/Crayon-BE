package com.yoyomo.domain.club.domain.service;

import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.club.domain.entity.ClubManager;
import com.yoyomo.domain.club.domain.repository.ClubMangerRepository;
import com.yoyomo.domain.club.exception.ClubManagerNotFoundException;
import com.yoyomo.domain.user.domain.entity.User;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClubManagerGetService {

    private final ClubMangerRepository clubMangerRepository;

    public ClubManager find(Club club, User manager) {
        return clubMangerRepository.findByClubAndManager(club, manager)
                .orElseThrow(ClubManagerNotFoundException::new);
    }

    public ClubManager findByUserId(Club club, long userId) {
        return clubMangerRepository.findByClubAndUserId(club, userId)
                .orElseThrow(ClubManagerNotFoundException::new);
    }

    public List<ClubManager> readAllManagers(UUID clubId) {
        return clubMangerRepository.findAllByClubId(clubId);
    }
}
