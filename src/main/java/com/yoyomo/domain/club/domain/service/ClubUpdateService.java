package com.yoyomo.domain.club.domain.service;

import com.yoyomo.domain.club.application.dto.request.ClubRequestDTO;
import com.yoyomo.domain.club.application.dto.request.ClubRequestDTO.Update;
import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.landing.application.dto.request.LandingRequestDTO;
import com.yoyomo.domain.landing.application.dto.request.LandingRequestDTO.General;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class ClubUpdateService {

    public void update(Club club, Update dto) {
        club.update(dto);
    }

    public void update(Club club, String notionPageLink) {
        club.update(notionPageLink);
    }

    public void update(Club club, General dto){
        club.update(dto);
    }

    public void delete(Club club) {
        club.delete();
    }

    public String update(Club club) {
        return club.generateCode();
    }
}
