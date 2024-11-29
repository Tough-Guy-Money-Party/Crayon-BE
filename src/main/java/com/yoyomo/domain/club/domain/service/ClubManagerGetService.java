package com.yoyomo.domain.club.domain.service;

import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.club.domain.entity.ClubManager;
import com.yoyomo.domain.club.domain.repository.ClubMangerRepository;
import com.yoyomo.domain.club.exception.ClubManagerNotFoundException;
import com.yoyomo.domain.user.domain.entity.Manager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClubManagerGetService {

    private final ClubMangerRepository clubMangerRepository;

    public ClubManager find(Club club, Manager manager) {
        return clubMangerRepository.findByClubAndManager(club, manager)
                .orElseThrow(ClubManagerNotFoundException::new);
    }

    public List<ClubManager> readAllManagers(UUID clubId) {
        return clubMangerRepository.findAllByClubId(clubId);
    }
}
