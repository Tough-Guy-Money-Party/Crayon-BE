package com.yoyomo.domain.club.domain.service;

import com.yoyomo.domain.club.application.dto.request.ClubRequestDTO.Save;
import com.yoyomo.domain.club.application.mapper.ClubMapper;
import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.club.domain.repository.ClubRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClubSaveService {

    private final ClubRepository clubRepository;
    private final ClubMapper clubMapper;

    public Club save(Save dto) {
        return clubRepository.save(clubMapper.from(dto));
    }
}
