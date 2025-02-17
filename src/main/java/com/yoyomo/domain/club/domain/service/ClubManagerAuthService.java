package com.yoyomo.domain.club.domain.service;

import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.club.domain.repository.ClubMangerRepository;
import com.yoyomo.domain.club.exception.ClubAccessDeniedException;
import com.yoyomo.domain.recruitment.domain.entity.Process;
import com.yoyomo.domain.recruitment.domain.entity.Recruitment;
import com.yoyomo.domain.recruitment.domain.repository.RecruitmentRepository;
import com.yoyomo.domain.recruitment.exception.RecruitmentNotFoundException;
import com.yoyomo.domain.user.domain.entity.User;
import com.yoyomo.domain.user.domain.repository.UserRepository;
import com.yoyomo.domain.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClubManagerAuthService {

    private final ClubMangerRepository clubMangerRepository;
    private final RecruitmentRepository recruitmentRepository;
    private final UserRepository userRepository;

    public void checkAuthorization(UUID recruitmentId, User manager) {
        Recruitment recruitment = recruitmentRepository.findById(recruitmentId)
                .orElseThrow(RecruitmentNotFoundException::new);

        Club club = recruitment.getClub();
        checkAuthorization(club, manager);
    }

    public void checkAuthorization(Process process, User manager) {
        Recruitment recruitment = process.getRecruitment();

        if (recruitment == null) {
            throw new RecruitmentNotFoundException();
        }

        Club club = recruitment.getClub();

        checkAuthorization(club, manager);
    }

    public void checkAuthorization(UUID recruitmentId, long userId) {
        User manager = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Recruitment recruitment = recruitmentRepository.findById(recruitmentId)
                .orElseThrow(RecruitmentNotFoundException::new);

        Club club = recruitment.getClub();
        checkAuthorization(club, manager);
    }

    public void checkAuthorization(Club club, User manager) {
        if (clubMangerRepository.existsByClubAndManager(club, manager)) {
            return;
        }
        throw new ClubAccessDeniedException();
    }
}
