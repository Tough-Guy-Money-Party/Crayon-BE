package com.yoyomo.domain.club.domain.service;

import com.yoyomo.domain.club.domain.entity.ClubManager;
import com.yoyomo.domain.club.domain.repository.ClubMangerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClubManagerDeleteService {

    private final ClubMangerRepository clubMangerRepository;

    public void delete(ClubManager clubManager) {
        clubMangerRepository.delete(clubManager);
    }
}
