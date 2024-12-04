package com.yoyomo.domain.club.domain.service;

import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.club.domain.repository.ClubMangerRepository;
import com.yoyomo.domain.club.domain.repository.ClubRepository;
import com.yoyomo.domain.club.exception.ClubNotFoundException;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClubGetService {

    private final ClubRepository clubRepository;
    private final ClubMangerRepository clubMangerRepository;

    public Club find(String clubId) {
        return clubRepository.findByIdAndDeletedAtIsNull(UUID.fromString(clubId))
                .orElseThrow(ClubNotFoundException::new);
    }

    public Club find(UUID clubId) {
        return clubRepository.findByIdAndDeletedAtIsNull(clubId)
                .orElseThrow(ClubNotFoundException::new);
    }

    public Club findBySubDomain(String subDomain) {
        return clubRepository.findBySubDomain(subDomain)
                .orElseThrow(ClubNotFoundException::new);
    }

    public boolean checkSubDomain(String subDomain) {
        return clubRepository.existsBySubDomain(subDomain);
    }

    public Club findByCode(String code) {
        return clubRepository.findByCode(code)
                .orElseThrow(ClubNotFoundException::new);
    }

    public List<Club> findAllByManagerId(long managerId) {
        return clubMangerRepository.findAllMyClubs(managerId);
    }
}
