package com.yoyomo.domain.landing.domain.service;

import org.springframework.stereotype.Service;

import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.landing.domain.entity.Landing;
import com.yoyomo.domain.landing.domain.repository.LandingRepository;
import com.yoyomo.domain.landing.exception.LandingNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LandingGetService {

	private final LandingRepository landingRepository;

	public Landing find(Club club) {
		return landingRepository.findByClub(club).orElseThrow(LandingNotFoundException::new);
	}
}
