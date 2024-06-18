package com.yoyomo.domain.club.application.usecase;

import com.yoyomo.domain.club.application.dto.req.ClubRequest;
import com.yoyomo.domain.club.application.dto.req.ParticipationRequest;
import com.yoyomo.domain.club.application.dto.req.RemoveManagerRequest;
import com.yoyomo.domain.club.application.dto.res.ClubCreateResponse;
import com.yoyomo.domain.club.application.dto.res.ClubManagerResponse;
import com.yoyomo.domain.club.application.dto.res.ClubResponse;
import com.yoyomo.domain.club.application.dto.res.ParticipationResponse;
import com.yoyomo.domain.club.application.mapper.ClubMapper;
import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.club.domain.service.ClubGetService;
import com.yoyomo.domain.club.domain.service.ClubSaveService;
import com.yoyomo.domain.club.domain.service.ClubUpdateService;
import com.yoyomo.domain.user.application.dto.res.ManagerResponse;
import com.yoyomo.global.config.participation.service.ParticipationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ClubManageUseCaseImpl implements ClubManageUseCase {
    private final ClubGetService clubGetService;
    private final ClubSaveService clubSaveService;
    private final ClubUpdateService clubUpdateService;
    private final ClubMapper clubMapper;
    private final ParticipationService participationService;

    public ClubResponse read(String id) {
        Club club = clubGetService.byId(id);
        return clubMapper.clubToClubResponse(club);
    }

    public ClubCreateResponse create(ClubRequest request, String userEmail) {
        Club club = clubMapper.from(request);
        club = clubSaveService.save(club);
        participationService.addToEachList(userEmail, club);
        return new ClubCreateResponse(club.getId());
    }

    @Override
    public ParticipationResponse participate(ParticipationRequest participationRequest, String userEmail) {
        ParticipationResponse response = participationService.checkAndParticipate(participationRequest.code(), userEmail);
        return response;
    }

    @Override
    public List<ClubManagerResponse> getManagers(String clubId) {
        Club club = clubGetService.byId(clubId);
        return Optional.ofNullable(club.getManagers())
                .orElse(Collections.emptyList())
                .stream()
                .map(manager -> ClubManagerResponse.builder()
                        .id(manager.getId())
                        .name(manager.getName())
                        .email(manager.getEmail())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public void removeManager(RemoveManagerRequest removeManagerRequest) {
        for (String userId : removeManagerRequest.userIds()) {
            participationService.deleteToEachList(userId, removeManagerRequest.clubId());
        }
    }


    public void update(String id, ClubRequest request) {
        clubUpdateService.from(id, request);
    }

    public void delete(String id) {
        clubUpdateService.delete(id);
    }
}
