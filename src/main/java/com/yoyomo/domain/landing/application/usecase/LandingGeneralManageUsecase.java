package com.yoyomo.domain.landing.application.usecase;

import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.club.domain.service.ClubGetService;
import com.yoyomo.domain.club.domain.service.ClubManagerAuthService;
import com.yoyomo.domain.club.domain.service.ClubUpdateService;
import com.yoyomo.domain.club.domain.service.ClubValidateService;
import com.yoyomo.domain.club.exception.DuplicatedSubDomainException;
import com.yoyomo.domain.landing.application.dto.request.CreateSubDomainRequest;
import com.yoyomo.domain.landing.application.dto.request.LandingRequestDTO;
import com.yoyomo.domain.landing.application.dto.request.LandingRequestDTO.General;
import com.yoyomo.domain.landing.application.dto.response.LandingResponseDTO;
import com.yoyomo.domain.landing.application.mapper.LandingMapper;
import com.yoyomo.domain.landing.domain.entity.Landing;
import com.yoyomo.domain.landing.domain.service.LandingGetService;
import com.yoyomo.domain.landing.domain.service.LandingUpdateService;
import com.yoyomo.domain.user.domain.entity.User;
import com.yoyomo.domain.user.domain.service.UserGetService;
import com.yoyomo.infra.aws.usecase.DistrubuteUsecase;
import java.io.IOException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LandingGeneralManageUsecase {

    private final ClubGetService clubGetService;
    private final ClubUpdateService clubUpdateService;
    private final LandingGetService landingGetService;
    private final LandingMapper landingMapper;
    private final DistrubuteUsecase distributeUsecaseImpl;
    private final LandingUpdateService landingUpdateService;
    private final ClubValidateService clubValidateService;
    private final UserGetService userGetService;
    private final ClubManagerAuthService clubManagerAuthService;

    @Transactional(readOnly = true)
    public LandingResponseDTO.General readGeneral(String clubId, long userId) {
        Club club = clubGetService.find(clubId);
        clubValidateService.checkAuthority(club.getId(), userId);

        Landing landing = landingGetService.find(club);
        return landingMapper.toGeneralResponse(club, landing);
    }

    @Transactional
    public void update(General dto, long userId) throws IOException {
        Club club = clubGetService.find(dto.clubId());
        clubValidateService.checkOwnerAuthority(club.getId(), userId);

        updateSubDomainIfChanged(dto, club);
        Landing landing = landingGetService.find(club);
        landingUpdateService.update(landing, club, dto);
    }

    private void updateSubDomainIfChanged(General dto, Club club) throws IOException {
        if (isSubDomainChanged(dto, club)) {
            String subDomain = checkDuplicatedSubDomain(dto.subDomain());
            String oldDomain = club.getSubDomain();

            //TODO: update 로직으로 변경 예정
            distributeUsecaseImpl.create(subDomain);
            distributeUsecaseImpl.delete(oldDomain);
        }
    }

    private String checkDuplicatedSubDomain(String subDomain) {
        if (clubGetService.checkSubDomain(subDomain)) {
            throw new DuplicatedSubDomainException();
        }
        return subDomain;
    }

    private boolean isSubDomainChanged(General dto, Club club) {
        return !dto.subDomain().equals(club.getSubDomain());
    }

    @Transactional
    public void update(LandingRequestDTO.NotionSave dto, long userId) {
        Club club = clubGetService.find(dto.clubId());
        clubValidateService.checkOwnerAuthority(club.getId(), userId);
        clubUpdateService.update(club, dto.notionPageLink());
    }

    @Transactional
    public void create(long userId, UUID clubId, CreateSubDomainRequest request) {
        User manager = userGetService.find(userId);
        Club club = clubGetService.find(clubId);
        clubManagerAuthService.checkAuthorization(club, manager);
        String subDomain = request.subDomain();

        clubValidateService.checkDuplicatedSubDomain(subDomain);
        distributeUsecaseImpl.create(subDomain);
        club.addSubDomain(request.subDomain());
    }
}
