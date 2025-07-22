package com.yoyomo.domain.recruitment.application.usecase;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.yoyomo.domain.ApplicationTest;
import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.application.domain.entity.enums.Rating;
import com.yoyomo.domain.application.domain.repository.ApplicationRepository;
import com.yoyomo.domain.application.domain.repository.EvaluationMemoRepository;
import com.yoyomo.domain.application.domain.repository.EvaluationRepository;
import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.club.domain.repository.ClubMangerRepository;
import com.yoyomo.domain.club.domain.repository.ClubRepository;
import com.yoyomo.domain.fixture.TestFixture;
import com.yoyomo.domain.recruitment.domain.entity.Process;
import com.yoyomo.domain.recruitment.domain.entity.Recruitment;
import com.yoyomo.domain.recruitment.domain.repository.ProcessRepository;
import com.yoyomo.domain.recruitment.domain.repository.RecruitmentRepository;
import com.yoyomo.domain.user.domain.entity.User;
import com.yoyomo.domain.user.domain.repository.UserRepository;

class RecruitmentManageUseCaseTest extends ApplicationTest {

	@Autowired
	UserRepository userRepository;

	@Autowired
	ClubMangerRepository clubMangerRepository;

	@Autowired
	ClubRepository clubRepository;

	@Autowired
	RecruitmentRepository recruitmentRepository;

	@Autowired
	ProcessRepository processRepository;

	@Autowired
	ApplicationRepository applicationRepository;

	@Autowired
	RecruitmentManageUseCase recruitmentManageUseCase;

	@Autowired
	EvaluationRepository evaluationRepository;

	@Autowired
	EvaluationMemoRepository evaluationMemoRepository;

	private Recruitment recruitment;
	private User manager;

	@BeforeEach
	void setUp() {
		manager = userRepository.save(TestFixture.user());
		User applicant = userRepository.save(TestFixture.user());
		User applicant2 = userRepository.save(TestFixture.user());
		Club club = clubRepository.save(TestFixture.club());
		clubMangerRepository.save(TestFixture.clubManager(club, manager));

		Process process = processRepository.save(TestFixture.process(1));
		recruitment = recruitmentRepository.save(TestFixture.recruitment(club, process));
		processRepository.save(process.addRecruitment(recruitment));

		Application application = applicationRepository.save(
			TestFixture.application(applicant, process, recruitment.getId()));
		Application application2 = applicationRepository.save(
			TestFixture.application(applicant2, process, recruitment.getId()));

		evaluationRepository.save(TestFixture.evaluation(application, applicant, Rating.HIGH));
		evaluationRepository.save(TestFixture.evaluation(application2, applicant, Rating.HIGH));

		evaluationMemoRepository.save(TestFixture.evaluationMemo(application, manager, process.getId()));
		evaluationMemoRepository.save(TestFixture.evaluationMemo(application2, manager, process.getId()));
	}

	@DisplayName("동아리 관리자는 모집을 삭제할 수 있다.")
	@Test
	void deleteRecruitment() {
		// when
		recruitmentManageUseCase.cancel(recruitment.getId().toString(), manager);

		// then
		assertThat(recruitmentRepository.findById(recruitment.getId())).isEmpty();
		List<Application> applications = applicationRepository.findAll();
		assertThat(evaluationRepository.findAll()).isEmpty();
		assertThat(evaluationMemoRepository.findAll()).isEmpty();
		assertThat(applications).isEmpty();
	}

	@DisplayName("동아리 관리자는 모집을 복제할 수 있다.")
	@Test
	void replicate() {
		// when
		UUID newRecruitmentId = recruitmentManageUseCase.replicate(recruitment.getId().toString(), manager);

		// then
		List<Recruitment> recruitments = recruitmentRepository.findAll();
		Recruitment newRecruitment = recruitments.stream()
			.filter(r -> r.getId().equals(newRecruitmentId))
			.findFirst()
			.orElseThrow();

		assertThat(recruitments).hasSize(2);
		assertThat(newRecruitment.getGeneration()).isEqualTo(recruitment.getGeneration());
		assertThat(newRecruitment.getTitle()).isEqualTo(recruitment.getTitle());
		assertThat(newRecruitment.getSubmit()).isEqualTo(recruitment.getSubmit());
		assertThat(newRecruitment.isActive()).isFalse();
		assertThat(newRecruitment.getStartAt()).isEqualToIgnoringNanos(recruitment.getStartAt());
		assertThat(newRecruitment.getEndAt()).isEqualToIgnoringNanos(recruitment.getEndAt());
		assertThat(newRecruitment.getCurrentProcess()).isEqualTo(recruitment.getCurrentProcess());
		assertThat(newRecruitment.getPosition()).isNull();

		List<Process> processes = processRepository.findAll();
		assertThat(processes).hasSize(2);
	}
}

