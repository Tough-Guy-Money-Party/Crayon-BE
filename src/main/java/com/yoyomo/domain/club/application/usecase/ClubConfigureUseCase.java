package com.yoyomo.domain.club.application.usecase;

import static com.yoyomo.domain.club.application.dto.request.ClubRequest.*;
import static com.yoyomo.domain.club.application.dto.response.ClubResponse.*;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.yoyomo.domain.club.application.mapper.ClubMapper;
import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.club.domain.service.ClubSaveService;
import com.yoyomo.domain.club.domain.service.ClubUpdateService;
import com.yoyomo.domain.club.domain.service.ClubValidateService;
import com.yoyomo.domain.user.domain.entity.User;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClubConfigureUseCase {

	private final ClubSaveService clubSaveService;
	private final ClubUpdateService clubUpdateService;
	private final ClubValidateService clubValidateService;
	private final ClubMapper clubMapper;

	@Transactional
	public Response save(Save dto, User manager) {
		Club club = clubSaveService.save(clubMapper.from(dto), manager);

		return clubMapper.toResponse(club);
	}

	@Transactional
	public void update(UUID clubId, Update dto, User user) {
		Club club = clubValidateService.checkOwnerAuthority(clubId, user);

		clubUpdateService.update(club, dto);
	}

	@Transactional
	public void delete(UUID clubId, User user) {
		Club club = clubValidateService.checkOwnerAuthority(clubId, user);

		clubUpdateService.delete(club);
	}
}
