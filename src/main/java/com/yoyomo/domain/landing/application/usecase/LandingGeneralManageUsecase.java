package com.yoyomo.domain.landing.application.usecase;

import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.club.domain.service.ClubGetService;
import com.yoyomo.domain.club.domain.service.ClubUpdateService;
import com.yoyomo.domain.club.domain.service.ClubValidateService;
import com.yoyomo.domain.club.exception.DuplicatedSubDomainException;
import com.yoyomo.domain.landing.application.dto.request.CreateSubDomainRequest;
import com.yoyomo.domain.landing.application.dto.request.LandingRequestDTO;
import com.yoyomo.domain.landing.application.dto.request.LandingRequestDTO.General;
import com.yoyomo.domain.landing.application.dto.response.LandingResponseDTO;
import com.yoyomo.domain.landing.application.mapper.LandingMapper;
import com.yoyomo.domain.landing.domain.constant.ReservedSubDomain;
import com.yoyomo.domain.landing.domain.entity.Landing;
import com.yoyomo.domain.landing.domain.service.LandingGetService;
import com.yoyomo.domain.landing.domain.service.LandingSaveService;
import com.yoyomo.domain.landing.domain.service.LandingUpdateService;
import com.yoyomo.domain.user.domain.entity.User;
import com.yoyomo.infra.aws.dto.LandingClientUploadEvent;
import com.yoyomo.infra.aws.route53.service.Route53Service;
import com.yoyomo.infra.aws.usecase.DistributeUsecase;
import java.io.IOException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LandingGeneralManageUsecase {

    private final ClubGetService clubGetService;
    private final ClubUpdateService clubUpdateService;
    private final LandingGetService landingGetService;
    private final LandingMapper landingMapper;
    private final DistributeUsecase distributeUsecase;
    private final LandingUpdateService landingUpdateService;
    private final ClubValidateService clubValidateService;
    private final LandingSaveService landingSaveService;
    private final ApplicationEventPublisher publisher;
    private final Route53Service route53Service;

    @Transactional(readOnly = true)
    public LandingResponseDTO.General readGeneral(String clubId, User user) {
        Club club = clubGetService.find(clubId);
        clubValidateService.checkAuthority(club.getId(), user);

        Landing landing = landingGetService.find(club);
        return landingMapper.toGeneralResponse(club, landing);
    }

    @Transactional
    public void update(General dto, User user) throws IOException {
        Club club = clubGetService.find(dto.clubId());
        clubValidateService.checkOwnerAuthority(club.getId(), user);

        updateSubDomainIfChanged(dto, club);
        Landing landing = landingGetService.find(club);
        landingUpdateService.update(landing, club, dto);
    }

    private void updateSubDomainIfChanged(General dto, Club club) {
        if (isSubDomainChanged(dto, club)) {
            String subDomain = checkDuplicatedSubDomain(dto.subDomain());
            String oldDomain = club.getSubDomain();

            //TODO: update 로직으로 변경 예정
            distributeUsecase.create(subDomain);
            publisher.publishEvent(new LandingClientUploadEvent(subDomain));

            distributeUsecase.delete(oldDomain);
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
    public void update(LandingRequestDTO.NotionSave dto, User user) {
        Club club = clubGetService.find(dto.clubId());
        clubValidateService.checkOwnerAuthority(club.getId(), user);
        clubUpdateService.update(club, dto.notionPageLink());
    }

    @Transactional
    public void create(User user, UUID clubId, CreateSubDomainRequest request) {
        Club club = clubValidateService.checkOwnerAuthority(clubId, user);
        String subDomain = request.subDomain();

        checkReservedSubdomain(subDomain);
        clubValidateService.checkDuplicatedSubDomain(subDomain);
        route53Service.checkDuplication(subDomain);

        distributeUsecase.create(subDomain);
        publisher.publishEvent(new LandingClientUploadEvent(subDomain));

        club.addSubDomain(request.subDomain());
        Landing landing = landingSaveService.save(club);
        club.addLanding(landing);
    }

    public void checkReservedSubdomain(String subDomain) {
        checkReservedSubDomain(subDomain);
    }

    private void checkReservedSubDomain(String subDomain) {
        if (ReservedSubDomain.contains(subDomain)) {
            throw new DuplicatedSubDomainException();
        }
    }
}
