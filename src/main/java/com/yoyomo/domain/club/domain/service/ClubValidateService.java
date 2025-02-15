package com.yoyomo.domain.club.domain.service;

import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.club.domain.entity.ClubManager;
import com.yoyomo.domain.club.domain.repository.ClubMangerRepository;
import com.yoyomo.domain.club.domain.repository.ClubRepository;
import com.yoyomo.domain.club.exception.ClubAccessDeniedException;
import com.yoyomo.domain.club.exception.ClubNotFoundException;
import com.yoyomo.domain.club.exception.DuplicatedSubDomainException;
import com.yoyomo.domain.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClubValidateService {

    private final ClubRepository clubRepository;
    private final ClubMangerRepository clubMangerRepository;

    public Club checkAuthority(String clubId, User manager) {
        return checkAuthority(UUID.fromString(clubId), manager);
    }

    public Club checkOwnerAuthority(UUID clubId, User manager) {
        Club club = clubRepository.findByIdAndDeletedAtIsNull(clubId)
                .orElseThrow(ClubNotFoundException::new);

        ClubManager clubManager = clubMangerRepository.findByClubAndManager(club, manager)
                .orElseThrow(ClubAccessDeniedException::new);

        if (clubManager.isOwner()) {
            return club;
        }
        throw new ClubAccessDeniedException();
    }

    public Club checkAuthority(UUID clubId, User manager) {
        Club club = clubRepository.findByIdAndDeletedAtIsNull(clubId)
                .orElseThrow(ClubNotFoundException::new);

        if (clubMangerRepository.existsByClubAndManager(club, manager)) {
            return club;
        }
        throw new ClubAccessDeniedException();
    }

    public void checkDuplicatedSubDomain(String subDomain) {
        if (clubRepository.existsBySubDomain(subDomain)) {
            throw new DuplicatedSubDomainException();
        }
    }
}
