package com.yoyomo.domain.application.application.usecase;

import static com.yoyomo.domain.fixture.TestFixture.*;
import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.yoyomo.domain.ApplicationTest;
import com.yoyomo.domain.application.application.dto.request.ApplicationSaveRequest;
import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.item.application.dto.req.ItemRequest;
import com.yoyomo.domain.item.domain.entity.type.Type;
import com.yoyomo.domain.recruitment.domain.entity.Recruitment;
import com.yoyomo.domain.user.domain.entity.User;

class ApplyUseCaseTest extends ApplicationTest {

	@Autowired
	ApplyUseCase applyUseCase;

	private User user;
	private Recruitment recruitment;

	@BeforeEach
	void setUp() {
		User manager = userRepository.save(user());
		user = userRepository.save(user());
		Club club = clubRepository.save(club());
		clubMangerRepository.save(clubManager(club, manager));
		recruitment = recruitmentRepository.save(activeRecruitment(club));
		processRepository.save(process(1, recruitment));
	}

	@DisplayName("지원서를 작성한다")
	@Test
	void apply() {
		// given
		ItemRequest itemRequest = new ItemRequest(
			"질문",
			Type.SHORT_FORM,
			"설명",
			1,
			true,
			null,
			"답변",
			30,
			null,
			null,
			null
		);

		ApplicationSaveRequest request = new ApplicationSaveRequest(
			"이름",
			"이메일",
			"전화번호",
			List.of(itemRequest)
		);

		// when
		applyUseCase.apply(request, recruitment.getId(), user);

		// then
		List<Application> applications = applicationRepository.findAll();
		assertThat(applications).hasSize(1);
	}
}
