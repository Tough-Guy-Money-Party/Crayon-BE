package com.yoyomo.domain.landing.application.usecase;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.club.domain.service.ClubGetService;
import com.yoyomo.domain.club.domain.service.ClubValidateService;
import com.yoyomo.domain.landing.application.dto.request.LandingRequest;
import com.yoyomo.domain.landing.application.dto.response.LandingResponse;
import com.yoyomo.domain.landing.application.mapper.LandingMapper;
import com.yoyomo.domain.landing.domain.entity.Landing;
import com.yoyomo.domain.landing.domain.service.LandingGetService;
import com.yoyomo.domain.landing.domain.service.LandingUpdateService;
import com.yoyomo.domain.user.domain.entity.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LandingStyleManagementUsecase {

	private final ClubGetService clubGetService;
	private final LandingGetService landingGetService;
	private final LandingMapper landingMapper;
	private final LandingUpdateService landingUpdateService;
	private final ClubValidateService clubValidateService;

	@Transactional
	public void update(LandingRequest.Style dto, User user) {
		Club club = clubGetService.find(dto.clubId());
		clubValidateService.checkOwnerAuthority(club.getId(), user);

		Landing landing = landingGetService.find(club);
		landingUpdateService.update(landing, dto);
	}

	@Transactional(readOnly = true)
	public LandingResponse.Style read(String clubId, User user) {
		Club club = clubGetService.find(clubId);
		clubValidateService.checkAuthority(club.getId(), user);

		Landing landing = landingGetService.find(club);
		return landingMapper.toStyleResponse(club, landing);
	}
}
