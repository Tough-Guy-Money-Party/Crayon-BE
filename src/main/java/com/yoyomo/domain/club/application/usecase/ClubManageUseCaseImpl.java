package com.yoyomo.domain.club.application.usecase;

import com.yoyomo.domain.club.application.dto.req.*;
import com.yoyomo.domain.club.application.dto.res.*;
import com.yoyomo.domain.club.application.mapper.ClubMapper;
import com.yoyomo.domain.club.application.mapper.ClubStyleMapper;
import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.club.domain.repository.ClubRepository;
import com.yoyomo.domain.club.domain.service.ClubGetService;
import com.yoyomo.domain.club.domain.service.ClubSaveService;
import com.yoyomo.domain.club.domain.service.ClubUpdateService;
import com.yoyomo.domain.club.exception.ClubNotFoundException;
import com.yoyomo.domain.club.exception.DuplicatedSubDomainException;
import com.yoyomo.domain.user.domain.entity.Manager;
import com.yoyomo.domain.user.domain.repository.ManagerRepository;
import com.yoyomo.domain.user.domain.service.UserGetService;
import com.yoyomo.domain.user.exception.UserNotFoundException;
import com.yoyomo.global.config.participation.service.ParticipationService;
import com.yoyomo.global.config.s3.RoutingService;
import com.yoyomo.global.config.s3.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
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
    private final UserGetService userGetService;
    private final ClubRepository clubRepository;
    private final S3Service s3Service;
    private final RoutingService routingService;
    private final String BASEURL = ".crayon.land";

    public ClubResponse read(String id) {
        Club club = clubGetService.byId(id);
        return clubMapper.clubToClubResponse(club);
    }

    public ClubCreateResponse create(ClubRequest request, String userEmail) {

        //도메인 체크
        String subdomain = request.subDomain() + BASEURL;
        String subDoamin = checkDuplicate(request.subDomain());

        Club club = clubMapper.from(request);
        club = clubSaveService.save(club);

        //배포
        s3Service.createBucket(subDoamin);
        routingService.handleS3Upload(subdomain,"ap-northeast-2",subdomain);

        participationService.addToEachList(userEmail, club);
        return new ClubCreateResponse(club.getId(), request.subDomain());
    }

    @Override
    public ParticipationResponse participate(ParticipationRequest participationRequest, String userEmail) {
        return participationService.checkAndParticipate(participationRequest.code(), userEmail);
    }

    @Override
    public List<ClubManagerResponse> getManagers(Authentication authentication) {
        String email = authentication.getName();

        Manager findManager = userGetService.findByEmail(email);
        List<Club> clubs = findManager.getClubs();
        List<String> clubIds = clubGetService.extractClubIds(clubs);

        if (clubs.isEmpty()) {
            throw new ClubNotFoundException();
        }

        Club club = clubGetService.byId(clubIds.get(0));

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

    public String checkDuplicate(String subDomain) {
        if (clubRepository.findBySubDomain(subDomain).size()>0) {
            throw new DuplicatedSubDomainException();
        }
        return subDomain;
    }


    public void update(String id, ClubRequest request) {
        clubUpdateService.from(id, request);
    }

    public void delete(String id) {
        clubUpdateService.delete(id);
    }
}
