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
import com.yoyomo.global.common.util.SubdomainFormatter;
import com.yoyomo.infra.aws.dto.LandingCreateEvent;
import com.yoyomo.infra.aws.dto.LandingDeleteEvent;
import com.yoyomo.infra.aws.route53.service.Route53Service;
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

        updateSubdomainIfChanged(dto, club);
        Landing landing = landingGetService.find(club);
        landingUpdateService.update(landing, club, dto);
    }

    private void updateSubdomainIfChanged(General dto, Club club) {
        if (isSubDomainChanged(dto, club)) {
            String subdomain = SubdomainFormatter.formatSubdomain(dto.subDomain());
            String oldDomain = club.getSubDomain();

            checkReservedSubdomain(subdomain);
            clubValidateService.checkDuplicatedSubDomain(subdomain);
            route53Service.checkDuplication(subdomain);

            publisher.publishEvent(new LandingCreateEvent(subdomain));
            publisher.publishEvent(new LandingDeleteEvent(SubdomainFormatter.formatSubdomain(oldDomain)));
        }
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
        String subdomain = SubdomainFormatter.formatSubdomain(request.subDomain());

        checkReservedSubdomain(subdomain);
        clubValidateService.checkDuplicatedSubDomain(subdomain);
        route53Service.checkDuplication(subdomain);

        publisher.publishEvent(new LandingCreateEvent(subdomain));

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
