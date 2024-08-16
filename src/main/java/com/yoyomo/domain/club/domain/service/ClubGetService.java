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

    public Club find(UUID clubId) {
        return clubRepository.findById(clubId)
                .orElseThrow(ClubNotFoundException::new);
    }
}
