package com.yoyomo.domain.club.application.usecase;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.yoyomo.domain.club.application.dto.request.ClubManagerUpdateRequest;
import com.yoyomo.domain.club.application.dto.request.ClubRequest;
import com.yoyomo.domain.club.application.dto.response.ClubResponse;
import com.yoyomo.domain.club.application.mapper.ClubMapper;
import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.club.domain.entity.ClubManager;
import com.yoyomo.domain.club.domain.service.ClubGetService;
import com.yoyomo.domain.club.domain.service.ClubManagerDeleteService;
import com.yoyomo.domain.club.domain.service.ClubManagerGetService;
import com.yoyomo.domain.club.domain.service.ClubManagerSaveService;
import com.yoyomo.domain.club.domain.service.ClubUpdateService;
import com.yoyomo.domain.club.domain.service.ClubValidateService;
import com.yoyomo.domain.user.application.dto.response.UserResponse;
import com.yoyomo.domain.user.application.mapper.ManagerMapper;
import com.yoyomo.domain.user.domain.entity.User;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClubManagerUseCase {

	private final ClubGetService clubGetService;
	private final ClubUpdateService clubUpdateService;
	private final ClubValidateService clubValidateService;
	private final ClubMapper clubMapper;
	private final ClubManagerSaveService clubManagerSaveService;
	private final ClubManagerDeleteService clubManagerDeleteService;
	private final ClubManagerGetService clubManagerGetService;
	private final ManagerMapper managerMapper;

	public List<UserResponse.ManagerInfo> getManagers(UUID clubId, User user) {
		clubValidateService.checkAuthority(clubId, user);
		List<ClubManager> clubManagers = clubManagerGetService.readAllManagers(clubId);

		return clubManagers.stream()
			.map(ClubManager::getManager)
			.map(managerMapper::toManagerInfo)
			.toList();
	}

	@Transactional
	public ClubResponse.Participation participate(ClubRequest.Participation dto, User user) {
		Club club = clubGetService.findByCode(dto.code());

		clubManagerSaveService.saveManager(user, club);
		return clubMapper.toParticipation(club);
	}

	public ClubResponse.Code readCode(String clubId, User user) {
		Club club = clubValidateService.checkAuthority(clubId, user);

		return new ClubResponse.Code(club.getCode());
	}

	@Transactional
	public ClubResponse.Code updateCode(String clubId, User user) {
		Club club = clubValidateService.checkAuthority(clubId, user);

		String updatedCode = clubUpdateService.update(club);
		return new ClubResponse.Code(updatedCode);
	}

	@Transactional
	public void deleteManagers(ClubRequest.Delete dto, User user) {
		clubValidateService.checkOwnerAuthority(dto.clubId(), user);

		clubManagerDeleteService.delete(dto.clubId(), dto.userIds(), user);
	}

	@Transactional
	public void updateOwner(ClubManagerUpdateRequest dto, User user, UUID clubId) {
		Club club = clubValidateService.checkOwnerAuthority(clubId, user);

		ClubManager owner = clubManagerGetService.findByUserId(club, user);
		ClubManager manager = clubManagerGetService.findByUserId(club, dto.userId());

		manager.toOwner();
		owner.toManager();
	}
}
