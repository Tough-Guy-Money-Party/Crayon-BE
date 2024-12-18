package com.yoyomo.domain.club.domain.service;

import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.club.domain.entity.ClubManager;
import com.yoyomo.domain.club.domain.repository.ClubMangerRepository;
import com.yoyomo.domain.club.exception.ClubManagerNotFoundException;
import com.yoyomo.domain.user.domain.entity.User;
import com.yoyomo.domain.user.domain.repository.UserRepository;
import com.yoyomo.domain.user.exception.UserNotFoundException;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClubManagerGetService {

    private final ClubMangerRepository clubMangerRepository;
    private final UserRepository userRepository;

    public ClubManager find(Club club, User manager) {
        return clubMangerRepository.findByClubAndManager(club, manager)
                .orElseThrow(ClubManagerNotFoundException::new);
    }

    public ClubManager findByUserId(Club club, long userId) {
        return clubMangerRepository.findByClubAndUserId(club, userId)
                .orElseThrow(ClubManagerNotFoundException::new);
    }

    public ClubManager findByEmail(Club club, String email) {
        User user = userRepository.findByEmailAndDeletedAtIsNull(email)
                .orElseThrow(UserNotFoundException::new);

        return find(club, user);
    }

    public List<ClubManager> readAllManagers(UUID clubId) {
        return clubMangerRepository.findAllByClubId(clubId);
    }
}
