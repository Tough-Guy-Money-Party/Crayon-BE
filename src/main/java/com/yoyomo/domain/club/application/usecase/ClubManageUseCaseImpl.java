package com.yoyomo.domain.club.application.usecase;

import com.yoyomo.domain.club.application.dto.req.ClubRequest;
import com.yoyomo.domain.club.application.dto.req.ParticipationRequest;
import com.yoyomo.domain.club.application.dto.res.ClubCreateResponse;
import com.yoyomo.domain.club.application.dto.res.ClubResponse;
import com.yoyomo.domain.club.application.mapper.ClubMapper;
import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.club.domain.service.ClubGetService;
import com.yoyomo.domain.club.domain.service.ClubSaveService;
import com.yoyomo.domain.club.domain.service.ClubUpdateService;
import com.yoyomo.domain.club.exception.InvalidPaticipationCodeException;
import com.yoyomo.domain.user.domain.service.UserUpdateService;
import com.yoyomo.global.config.participation.ParticipationCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ClubManageUseCaseImpl implements ClubManageUseCase {
    private final ClubGetService clubGetService;
    private final ClubSaveService clubSaveService;
    private final ClubUpdateService clubUpdateService;
    private final ClubMapper clubMapper;

    private final UserUpdateService userUpdateService;
    private final ParticipationCodeService participationCodeService;

    public ClubResponse read(String id) {
        Club club = clubGetService.byId(id);
        return clubMapper.clubToClubResponse(club);
    }

    public ClubCreateResponse create(ClubRequest request, String userEmail) {
        Club club = clubMapper.from(request);
        club = clubSaveService.save(club);

        clubUpdateService.addUser(userEmail, club.getId());
        userUpdateService.addClub(userEmail, club.getId());

        String id = club.getId();
        return new ClubCreateResponse(id);
    }
    @Override
    public void participate(ParticipationRequest participationRequest, String userEmail, String clubId) {
        if (participationCodeService.isValidCode(participationRequest.code(), clubId)){
            clubUpdateService.addUser(userEmail, clubId);
        }else{
            throw new InvalidPaticipationCodeException();
        }
    }

    public void update(String id, ClubRequest request) {
        clubUpdateService.from(id, request);
    }

    public void delete(String id) {
        clubUpdateService.delete(id);
    }
}
