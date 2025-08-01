package com.yoyomo.domain.landing.domain.service;

import org.springframework.stereotype.Service;

import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.landing.domain.entity.Landing;
import com.yoyomo.domain.landing.domain.repository.LandingRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LandingSaveService {

	private final LandingRepository landingRepository;

	public Landing save(Club club) {
		return landingRepository.save(new Landing(club));
	}
}
