package com.yoyomo.domain.club.application.usecase;

import com.yoyomo.domain.club.application.dto.response.ClubResponseDTO;
import com.yoyomo.domain.club.application.mapper.ClubMapper;
import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.club.domain.service.ClubGetService;
import com.yoyomo.domain.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClubReadUseCase {

    private final ClubGetService clubGetService;
    private final ClubMapper clubMapper;

    public List<ClubResponseDTO.Response> readAll(User manager) {
        List<Club> myClubs = clubGetService.findAllByManager(manager);

        return myClubs.stream()
                .map(clubMapper::toResponse)
                .toList();
    }

    public ClubResponseDTO.Response read(String clubId) {
        Club club = clubGetService.find(clubId);
        return clubMapper.toResponse(club);
    }
}
