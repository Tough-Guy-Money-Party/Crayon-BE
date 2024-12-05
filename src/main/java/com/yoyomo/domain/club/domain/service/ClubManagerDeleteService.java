package com.yoyomo.domain.club.domain.service;

import com.yoyomo.domain.club.domain.repository.ClubMangerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClubManagerDeleteService {

    private final ClubMangerRepository clubMangerRepository;

    public void delete(List<Long> clubManagerIds) {
        clubMangerRepository.deleteAllById(clubManagerIds);
    }
}
