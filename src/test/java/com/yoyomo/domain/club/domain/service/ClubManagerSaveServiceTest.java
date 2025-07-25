package com.yoyomo.domain.club.domain.service;

import static com.yoyomo.domain.fixture.TestFixture.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.yoyomo.domain.ApplicationTest;
import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.club.domain.entity.ClubManager;
import com.yoyomo.domain.club.domain.entity.ManagerRole;
import com.yoyomo.domain.club.domain.repository.ClubMangerRepository;
import com.yoyomo.domain.club.domain.repository.ClubRepository;
import com.yoyomo.domain.club.exception.DuplicatedParticipationException;
import com.yoyomo.domain.user.domain.entity.User;
import com.yoyomo.domain.user.domain.repository.UserRepository;

class ClubManagerSaveServiceTest extends ApplicationTest {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ClubRepository clubRepository;

	@Autowired
	private ClubManagerSaveService clubManagerSaveService;

	@Autowired
	private ClubMangerRepository clubMangerRepository;

	@DisplayName("동아리 관리자로 저장한다.")
	@Test
	void saveManager() {
		// given
		User user = userRepository.save(user());
		Club club = clubRepository.save(club());

		// when
		ClubManager clubManager = clubManagerSaveService.saveManager(user, club);

		// then
		assertThat(clubMangerRepository.findById(clubManager.getId()))
			.isPresent()
			.hasValueSatisfying(manager ->
				assertThat(manager.getManagerRole()).isEqualTo(ManagerRole.MANAGER)
			);
	}

	@DisplayName("이미 존재하는 관리자라면 예외를 발생한다.")
	@Test
	void saveAlreadyExistsManager_throwException() {
		// given
		User user = userRepository.save(user());
		Club club = clubRepository.save(club());
		clubMangerRepository.save(ClubManager.asManager(club, user));

		// when & then
		assertThatThrownBy(() -> clubManagerSaveService.saveManager(user, club))
			.isInstanceOf(DuplicatedParticipationException.class);
	}
}
