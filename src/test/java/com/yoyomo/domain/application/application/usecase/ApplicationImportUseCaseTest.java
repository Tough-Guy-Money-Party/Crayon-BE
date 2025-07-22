package com.yoyomo.domain.application.application.usecase;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.yoyomo.domain.ApplicationTest;
import com.yoyomo.domain.application.application.dto.request.ApplicationImportRequest;
import com.yoyomo.domain.application.application.dto.request.DataRequest;
import com.yoyomo.domain.application.application.dto.request.QuestionRequest;
import com.yoyomo.domain.application.application.dto.request.RespondentRequest;
import com.yoyomo.domain.application.domain.entity.Answer;
import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.application.domain.repository.ApplicationRepository;
import com.yoyomo.domain.application.domain.repository.mongo.AnswerRepository;
import com.yoyomo.domain.application.exception.QuestionReplySizeMismatchException;
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

class ApplicationImportUseCaseTest extends ApplicationTest {

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
	AnswerRepository answerRepository;

	@Autowired
	ApplicationImportUseCase applicationImportUseCase;

	@DisplayName("지원서를 일괄 등록한다")
	@Test
	void importApplications() {
		// given
		User user = userRepository.save(TestFixture.user());
		Club club = clubRepository.save(TestFixture.club());
		clubMangerRepository.save(TestFixture.clubManager(club, user));

		Process process = processRepository.save(TestFixture.process(1));
		Recruitment recruitment = recruitmentRepository.save(TestFixture.recruitment(club, process));
		processRepository.save(process.addRecruitment(recruitment));

		List<QuestionRequest> questionRequests = List.of(
			new QuestionRequest("이름을 적어", "string"),
			new QuestionRequest("입력해라 전화번호", "string"),
			new QuestionRequest("언제볼래", "datetime"),
			new QuestionRequest("배고프세요?", "string"),
			new QuestionRequest("졸리세요?", "string")
		);

		List<RespondentRequest> respondentRequests = List.of(
			new RespondentRequest(
				List.of(
					new DataRequest("나아연"),
					new DataRequest("01012345678"),
					new DataRequest("Date(2025,05,30,19,36,00)"),
					new DataRequest("죽겠어요"),
					new DataRequest(null)
				)
			),
			new RespondentRequest(
				List.of(
					new DataRequest("이근표"),
					new DataRequest("01087654321"),
					new DataRequest("Date(2025,05,30,19,36,00)"),
					new DataRequest(null),
					new DataRequest("기절")
				)
			)
		);

		ApplicationImportRequest applicationImportRequest = new ApplicationImportRequest(
			questionRequests, respondentRequests);

		// when
		applicationImportUseCase.importApplications(recruitment.getId(), user, applicationImportRequest);

		// then
		assertThat(recruitmentRepository.findById(recruitment.getId()))
			.isPresent()
			.hasValueSatisfying(result ->
				assertThat(result.getTotalApplicantsCount()).isEqualTo(2)
			);

		List<Application> applications = applicationRepository.findAll();
		assertAll(
			() -> assertThat(applications).hasSize(2),
			() -> assertThat(applications.get(0).getUserName()).isEqualTo("나아연"),
			() -> assertThat(applications.get(0).getTel()).isEqualTo("01012345678"),
			() -> assertThat(applications.get(0).getEmail()).isEmpty()
		);

		List<Answer> answers = answerRepository.findAll();
		assertAll(
			() -> assertThat(answers).hasSize(2),
			() -> assertThat(answers.get(0).getApplicationId()).isNotNull(),
			() -> assertThat(answers.get(0).getItems()).hasSize(3)
		);
	}

	@DisplayName("지원서 일괄 등록 시 질문과 응답의 크기가 일치하지 않으면 예외가 발생한다")
	@Test
	void importApplications_MismatchSizeException() {
		// given
		User user = userRepository.save(TestFixture.user());
		Club club = clubRepository.save(TestFixture.club());
		clubMangerRepository.save(TestFixture.clubManager(club, user));

		Process process = processRepository.save(TestFixture.process(1));
		Recruitment recruitment = recruitmentRepository.save(TestFixture.recruitment(club, process));
		processRepository.save(process.addRecruitment(recruitment));

		List<QuestionRequest> questionRequests = List.of(
			new QuestionRequest("이름을 적어", "string"),
			new QuestionRequest("입력해라 전화번호", "string"),
			new QuestionRequest("언제볼래", "datetime"),
			new QuestionRequest("배고프세요?", "string"),
			new QuestionRequest("졸리세요?", "string")
		);

		List<RespondentRequest> respondentRequests = List.of(
			new RespondentRequest(
				List.of(
					new DataRequest("나아연"),
					new DataRequest("01012345678"),
					new DataRequest("Date(2025,05,30,19,36,00)"),
					new DataRequest("죽겠어요"),
					new DataRequest(null)
				)
			),
			new RespondentRequest(
				List.of(
					new DataRequest("이근표"),
					new DataRequest("01087654321"),
					new DataRequest("Date(2025,05,30,19,36,00)"),
					new DataRequest("기절")
				)
			)
		);

		ApplicationImportRequest applicationImportRequest = new ApplicationImportRequest(
			questionRequests, respondentRequests
		);

		// when & then
		assertThatThrownBy(
			() -> applicationImportUseCase.importApplications(recruitment.getId(), user, applicationImportRequest))
			.isInstanceOf(QuestionReplySizeMismatchException.class);
	}
}
