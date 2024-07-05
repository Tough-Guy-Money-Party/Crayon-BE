package com.yoyomo.domain.recruitment.domain.service;

import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.club.domain.service.ClubGetService;
import com.yoyomo.domain.club.exception.ClubNotFoundException;
import com.yoyomo.domain.recruitment.domain.entity.Recruitment;
import com.yoyomo.domain.recruitment.domain.repository.RecruitmentRepository;
import com.yoyomo.domain.recruitment.exception.RecruitmentNotFoundException;
import com.yoyomo.domain.user.domain.entity.Manager;
import com.yoyomo.domain.user.domain.service.UserGetService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecruitmentGetService {
    private final RecruitmentRepository recruitmentRepository;
    private final ClubGetService clubGetService;
    private final UserGetService userGetService;

    public Recruitment find(String id) {
        return recruitmentRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(RecruitmentNotFoundException::new);
    }

    public LocalDate getRecruitmentEndDate(String id) {
        Recruitment recruitment = recruitmentRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(RecruitmentNotFoundException::new);
        int lastIndex = recruitment.getProcesses().size() - 1;
        return recruitment.getProcesses().get(lastIndex).getEndAt();
    }

    public Page<Recruitment> findAll(String clubId, PageRequest pageRequest) {
        return recruitmentRepository.findAllByClubIdAndDeletedAtIsNull(clubId, pageRequest);
    }

    public String getClubId(String email) {

        Manager manager = userGetService.findByEmail(email);
        List<Club> clubs = manager.getClubs();
        List<String> clubIds = clubGetService.extractClubIds(clubs);

        String clubId = clubIds.get(0);

        return clubId;
    }

    public Recruitment findAnnouncedRecruitment(String clubId) {
        Recruitment recruitments = recruitmentRepository.findByClubId(clubId)
                .orElseThrow(ClubNotFoundException::new);
        recruitments.remainOnlyAnnouncedProcess();
        return recruitments;
    }
}
