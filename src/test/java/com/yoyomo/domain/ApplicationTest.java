package com.yoyomo.domain;

import org.junit.jupiter.api.AfterEach;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.ActiveProfiles;

import com.yoyomo.domain.application.application.usecase.ApplicationVerifyUseCase;
import com.yoyomo.domain.application.domain.repository.ApplicationRepository;
import com.yoyomo.domain.club.domain.repository.ClubMangerRepository;
import com.yoyomo.domain.club.domain.repository.ClubRepository;
import com.yoyomo.domain.fixture.CustomRepository;
import com.yoyomo.domain.recruitment.domain.repository.ProcessRepository;
import com.yoyomo.domain.recruitment.domain.repository.RecruitmentRepository;
import com.yoyomo.domain.user.domain.repository.UserRepository;
import com.yoyomo.global.config.jwt.JwtProvider;
import com.yoyomo.global.config.kakao.KakaoServiceNew;

@MockBean(classes = {
	ApplicationVerifyUseCase.class,
	KakaoServiceNew.class,
	JwtProvider.class,
	RedissonClient.class
})
@ComponentScan(
	excludeFilters = @ComponentScan.Filter(
		type = FilterType.REGEX,
		pattern = {
			"com.yoyomo.domain.mail.*",
			"com.yoyomo.domain.landing.*",
			"com.yoyomo.domain.template.*"
		}
	)
)
@SpringBootTest
@ActiveProfiles("test")
public class ApplicationTest {

	@Autowired
	CustomRepository customRepository;

	@Autowired
	protected UserRepository userRepository;

	@Autowired
	protected ClubRepository clubRepository;

	@Autowired
	protected ClubMangerRepository clubMangerRepository;

	@Autowired
	protected RecruitmentRepository recruitmentRepository;

	@Autowired
	protected ProcessRepository processRepository;

	@Autowired
	protected ApplicationRepository applicationRepository;

	@AfterEach
	void tearDown() {
		customRepository.clearAndReset();
	}
}
