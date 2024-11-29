package com.yoyomo.domain.club.application.usecase;

import com.yoyomo.domain.club.application.dto.response.ClubResponseDTO;
import com.yoyomo.domain.club.application.mapper.ClubMapperImpl;
import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.club.domain.service.ClubGetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClubReadUseCase {

    private final ClubGetService clubGetService;
    private final ClubMapperImpl clubMapper;

    public List<ClubResponseDTO.Response> readAll(long managerId) {
        List<Club> myClubs = clubGetService.findAllByManagerId(managerId);

        return myClubs.stream()
                .map(clubMapper::toResponse)
                .toList();
    }

    public ClubResponseDTO.Response read(String clubId) {
        Club club = clubGetService.find(clubId);
        return clubMapper.toResponse(club);
    }
}
