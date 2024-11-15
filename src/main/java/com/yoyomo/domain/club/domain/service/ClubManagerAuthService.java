package com.yoyomo.domain.club.domain.service;

import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.club.domain.entity.ClubManager;
import com.yoyomo.domain.club.domain.repository.ClubMangerRepository;
import com.yoyomo.domain.club.exception.ClubAccessDeniedException;
import com.yoyomo.domain.recruitment.domain.entity.Recruitment;
import com.yoyomo.domain.recruitment.domain.repository.RecruitmentRepository;
import com.yoyomo.domain.recruitment.exception.RecruitmentNotFoundException;
import com.yoyomo.domain.user.domain.entity.Manager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClubManagerAuthService {

    private final ClubMangerRepository clubMangerRepository;
    private final RecruitmentRepository recruitmentRepository;

    public void checkAuthorization(UUID recruitmentId, Manager manager) {
        Recruitment recruitment = recruitmentRepository.findById(recruitmentId)
                .orElseThrow(RecruitmentNotFoundException::new);

        Club club = recruitment.getClub();
        checkAuthorization(club, manager);
    }

    public void checkAuthorization(Club club, Manager manager) {
        List<Manager> managers = clubMangerRepository.findAllByClubId(club.getId())
                .stream()
                .map(ClubManager::getManager)
                .toList();


        if (!managers.contains(manager)) {
            throw new ClubAccessDeniedException();
        }
    }
}
