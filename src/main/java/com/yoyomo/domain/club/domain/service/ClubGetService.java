package com.yoyomo.domain.club.domain.service;

import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.club.domain.repository.ClubRepository;
import com.yoyomo.domain.club.exception.ClubNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClubGetService {

    private final ClubRepository clubRepository;

    public Club find(String clubId) {
        return clubRepository.findByIdAndDeletedAtIsNull(UUID.fromString(clubId))
                .orElseThrow(ClubNotFoundException::new);
    }

    public boolean checkSubDomain(String subDomain) {
        return clubRepository.existsBySubDomain(subDomain);
    }

    public Club findByCode(String code) {
        return clubRepository.findByCode(code)
                .orElseThrow(ClubNotFoundException::new);
    }
}
