package com.yoyomo.domain.club.domain.service;

import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.club.domain.repository.ClubMangerRepository;
import com.yoyomo.domain.club.domain.repository.ClubRepository;
import com.yoyomo.domain.club.exception.ClubAccessDeniedException;
import com.yoyomo.domain.club.exception.ClubNotFoundException;
import com.yoyomo.domain.club.exception.DuplicatedSubDomainException;
import com.yoyomo.domain.user.domain.entity.User;
import com.yoyomo.domain.user.domain.repository.UserRepository;
import com.yoyomo.domain.user.exception.UserNotFoundException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClubValidateService {

    private final ClubRepository clubRepository;
    private final UserRepository userRepository;
    private final ClubMangerRepository clubMangerRepository;

    public Club checkAuthority(String clubId, long userId) {
        return checkAuthority(UUID.fromString(clubId), userId);
    }

    public Club checkAuthority(UUID clubId, long userId) {
        Club club = clubRepository.findByIdAndDeletedAtIsNull(clubId)
                .orElseThrow(ClubNotFoundException::new);
        User manager = userRepository.findByIdAndDeletedAtIsNull(userId)
                .orElseThrow(UserNotFoundException::new);

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
