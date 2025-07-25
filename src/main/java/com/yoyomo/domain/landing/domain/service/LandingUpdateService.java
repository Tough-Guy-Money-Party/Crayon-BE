package com.yoyomo.domain.landing.domain.service;

import org.springframework.stereotype.Service;

import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.club.domain.service.ClubUpdateService;
import com.yoyomo.domain.landing.application.dto.request.LandingRequest.General;
import com.yoyomo.domain.landing.application.dto.request.LandingRequest.Style;
import com.yoyomo.domain.landing.domain.entity.Landing;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LandingUpdateService {

	private final ClubUpdateService clubUpdateService;

	public void update(Landing landing, Style dto) {
		landing.updateStyle(dto);
	}

	public void update(Landing landing, Club club, General dto) {
		landing.updateGeneral(dto);
		clubUpdateService.update(club, dto);
	}
}
