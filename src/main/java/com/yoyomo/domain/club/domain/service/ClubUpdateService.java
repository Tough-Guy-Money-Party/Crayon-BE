package com.yoyomo.domain.club.domain.service;

import com.yoyomo.domain.club.application.dto.request.ClubRequestDTO;
import com.yoyomo.domain.club.domain.entity.Club;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class ClubUpdateService {

    public void update(Club club, ClubRequestDTO.Save dto) {
        club.update(dto);
    }

    public void delete(Club club) {
        club.delete();
    }

    public String update(Club club) {
        return club.initCode();
    }
}
