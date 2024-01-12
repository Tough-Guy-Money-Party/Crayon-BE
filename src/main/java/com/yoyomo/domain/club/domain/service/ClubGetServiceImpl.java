package com.yoyomo.domain.club.domain.service;

import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.club.domain.repository.ClubRepository;
import com.yoyomo.domain.club.exception.ClubNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClubGetServiceImpl implements ClubGetService {
    private final ClubRepository clubRepository;

    public Club byId(String id) {
        return clubRepository.findById(id).orElseThrow(ClubNotFoundException::new);
    }
}
