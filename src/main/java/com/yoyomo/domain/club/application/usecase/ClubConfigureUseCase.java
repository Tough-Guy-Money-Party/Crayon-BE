package com.yoyomo.domain.club.application.usecase;

import static com.yoyomo.domain.club.application.dto.request.ClubRequestDTO.Save;
import static com.yoyomo.domain.club.application.dto.request.ClubRequestDTO.Update;
import static com.yoyomo.domain.club.application.dto.response.ClubResponseDTO.Response;

import com.yoyomo.domain.club.application.mapper.ClubMapper;
import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.club.domain.service.ClubSaveService;
import com.yoyomo.domain.club.domain.service.ClubUpdateService;
import com.yoyomo.domain.club.domain.service.ClubValidateService;
import com.yoyomo.domain.user.domain.entity.User;
import com.yoyomo.domain.user.domain.service.UserGetService;
import jakarta.transaction.Transactional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClubConfigureUseCase {

    private final UserGetService userGetService;
    private final ClubSaveService clubSaveService;
    private final ClubUpdateService clubUpdateService;
    private final ClubValidateService clubValidateService;
    private final ClubMapper clubMapper;

    @Transactional
    public Response save(Save dto, Long userId) {
        User manager = userGetService.find(userId);
        Club club = clubSaveService.save(clubMapper.from(dto), manager);

        return clubMapper.toResponse(club);
    }

    @Transactional
    public void update(UUID clubId, Update dto, Long userId) {
        Club club = clubValidateService.checkOwnerAuthority(clubId, userId);

        clubUpdateService.update(club, dto);
    }

    @Transactional
    public void delete(UUID clubId, Long userId) {
        Club club = clubValidateService.checkOwnerAuthority(clubId, userId);

        clubUpdateService.delete(club);
    }
}
