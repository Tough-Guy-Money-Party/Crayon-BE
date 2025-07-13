package com.yoyomo.domain.application.domain.repository;

import static com.yoyomo.domain.fixture.TestFixture.*;
import static java.util.stream.Collectors.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;

import com.yoyomo.domain.RepositoryTest;
import com.yoyomo.domain.application.application.dto.request.ApplicationCondition;
import com.yoyomo.domain.application.application.dto.request.condition.EvaluationFilter;
import com.yoyomo.domain.application.application.dto.request.condition.ResultFilter;
import com.yoyomo.domain.application.application.dto.request.condition.SortType;
import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.application.domain.entity.Evaluation;
import com.yoyomo.domain.application.domain.entity.ProcessResult;
import com.yoyomo.domain.application.domain.entity.enums.Rating;
import com.yoyomo.domain.application.domain.entity.enums.Status;
import com.yoyomo.domain.application.domain.repository.dto.ApplicationWithStatus;
import com.yoyomo.domain.recruitment.domain.entity.Process;
import com.yoyomo.domain.recruitment.domain.repository.ProcessRepository;
import com.yoyomo.domain.user.domain.entity.User;
import com.yoyomo.domain.user.domain.repository.UserRepository;

class ApplicationRepositoryTest extends RepositoryTest {

	@Autowired
	ProcessRepository processRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	ApplicationRepository applicationRepository;

	@Autowired
	ProcessResultRepository processResultRepository;

	@Autowired
	EvaluationRepository evaluationRepository;

	@Autowired
	JdbcTemplate jdbcTemplate;

	private Process process;
	private List<Application> applications;
	private Map<Status, List<UUID>> statusApplicationIds;
	private UUID withoutResultApplicationId;

	@BeforeEach
	void setUp() {
		process = processRepository.save(process());

		List<String> usernames = List.of("나아연", "이근표", "김성민", "이한별", "조혜원");

		IntStream.range(0, 5)
			.mapToObj(i -> userRepository.save(user(usernames.get(i))))
			.forEach(user -> jdbcTemplate.update(
				"""
					INSERT INTO application(application_id, user_id, process_id, created_at, recruitment_id, user_name)
					VALUES (?, ?, ?, ?, ?, ?)
					""",
				ps -> {
					ps.setBytes(1, uuidToBytes(UUID.randomUUID()));
					ps.setLong(2, user.getId());
					ps.setLong(3, process.getId());
					ps.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
					ps.setBytes(5, uuidToBytes(UUID.randomUUID()));
					ps.setString(6, user.getName());
				}));

		applications = applicationRepository.findAll()
			.stream()
			.toList();

		List<ProcessResult> processResults = List.of(
			processResult(applications.get(0).getId(), process.getId(), Status.DOCUMENT_PASS),
			processResult(applications.get(1).getId(), process.getId(), Status.PENDING),
			processResult(applications.get(2).getId(), process.getId(), Status.DOCUMENT_FAIL),
			processResult(applications.get(3).getId(), process.getId(), Status.PENDING)
		);
		processResultRepository.saveAll(processResults);

		statusApplicationIds = processResults.stream()
			.collect(
				groupingBy(ProcessResult::getStatus,
					mapping(ProcessResult::getApplicationId, Collectors.toList()))
			);
		withoutResultApplicationId = applications.get(4).getId();
	}

	private byte[] uuidToBytes(UUID uuid) {
		ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
		bb.putLong(uuid.getMostSignificantBits());
		bb.putLong(uuid.getLeastSignificantBits());
		return bb.array();
	}

	@Nested
	class ResultFilterTest {

		@DisplayName("합격 지원서만 필터링한다")
		@Test
		void findAllWithStatusByProcess_filterPass() {
			ApplicationCondition condition = new ApplicationCondition(
				SortType.APPLIED, EvaluationFilter.ALL, ResultFilter.PASS, PageRequest.of(0, 5));

			List<ApplicationWithStatus> passApplications = applicationRepository.findAllWithStatusByProcess(
					process, condition, condition.pageRequest())
				.getContent();

			assertAll(
				() -> assertThat(passApplications).hasSize(1),
				() -> assertThat(passApplications.get(0).application().getId())
					.isIn(statusApplicationIds.get(Status.DOCUMENT_PASS))
			);
		}

		@DisplayName("불합격 지원서만 필터링한다")
		@Test
		void findAllWithStatusByProcess_filterFail() {
			ApplicationCondition condition = new ApplicationCondition(
				SortType.APPLIED,
				EvaluationFilter.ALL,
				ResultFilter.FAIL,
				PageRequest.of(0, 5)
			);

			List<ApplicationWithStatus> failApplications = applicationRepository.findAllWithStatusByProcess(
				process, condition, condition.pageRequest()
			).getContent();

			assertAll(
				() -> assertThat(failApplications).hasSize(1),
				() -> assertThat(failApplications.get(0).application().getId())
					.isIn(statusApplicationIds.get(Status.DOCUMENT_FAIL))
			);
		}

		@DisplayName("보류 지원서만 필터링한다")
		@Test
		void findAllWithStatusByProcess_filterPending() {
			ApplicationCondition condition = new ApplicationCondition(
				SortType.APPLIED,
				EvaluationFilter.ALL,
				ResultFilter.PENDING,
				PageRequest.of(0, 5)
			);

			List<ApplicationWithStatus> pendingApplications = applicationRepository.findAllWithStatusByProcess(
				process, condition, condition.pageRequest()
			).getContent();

			assertAll(
				() -> assertThat(pendingApplications).hasSize(2),
				() -> assertThat(pendingApplications.get(0).application().getId())
					.isIn(statusApplicationIds.get(Status.PENDING)),
				() -> assertThat(pendingApplications.get(1).application().getId())
					.isIn(statusApplicationIds.get(Status.PENDING))
			);
		}

		@DisplayName("평가 전 지원서만 필터링한다")
		@Test
		void findAllWithStatusByProcess_filterWithoutResult() {
			ApplicationCondition condition = new ApplicationCondition(
				SortType.APPLIED,
				EvaluationFilter.ALL,
				ResultFilter.NONE,
				PageRequest.of(0, 5)
			);

			List<ApplicationWithStatus> noneResultApplications = applicationRepository.findAllWithStatusByProcess(
				process, condition, condition.pageRequest()
			).getContent();

			assertAll(
				() -> assertThat(noneResultApplications).hasSize(1),
				() -> assertThat(noneResultApplications.get(0).application().getId())
					.isEqualTo(withoutResultApplicationId)
			);
		}
	}

	@Nested
	class EvaluationTest {

		private int hasEvalautionCount = 2;
		private int hasNotEvalautionCount = 3;
		private Set<UUID> hasEvaluationApplicationIds = new HashSet<>();
		private Set<UUID> hasNotEvaluationApplicationIds = new HashSet<>();

		@BeforeEach
		void setUp() {
			for (int i = 0; i < hasEvalautionCount + hasNotEvalautionCount; i++) {
				if (i < hasEvalautionCount) {
					User user = userRepository.save(user());
					Evaluation saved = evaluationRepository.save(evaluation(applications.get(i), user, Rating.HIGH));
					hasEvaluationApplicationIds.add(saved.getApplication().getId());
				} else {
					hasNotEvaluationApplicationIds.add(applications.get(i).getId());
				}
			}
		}

		@DisplayName("개인 평가 완료 지원서만 필터링한다")
		@Test
		void findAllWithStatusByProcess_hasEvaluation() {
			ApplicationCondition condition = new ApplicationCondition(
				SortType.APPLIED,
				EvaluationFilter.YES,
				ResultFilter.ALL,
				PageRequest.of(0, 5)
			);

			List<UUID> result = applicationRepository.findAllWithStatusByProcess(
					process, condition, condition.pageRequest())
				.getContent()
				.stream()
				.map(applicationWithStatus -> applicationWithStatus.application().getId())
				.toList();

			assertAll(
				() -> assertThat(result).hasSize(hasEvalautionCount),
				() -> assertThat(result).containsAll(hasEvaluationApplicationIds)
			);
		}

		@DisplayName("개인 평가 미완료 지원서만 필터링한다")
		@Test
		void findAllWithStatusByProcess_hasNotEvaluation() {
			ApplicationCondition condition = new ApplicationCondition(
				SortType.APPLIED,
				EvaluationFilter.NO,
				ResultFilter.ALL,
				PageRequest.of(0, 5)
			);

			List<UUID> result = applicationRepository.findAllWithStatusByProcess(
					process, condition, condition.pageRequest())
				.getContent()
				.stream()
				.map(applicationWithStatus -> applicationWithStatus.application().getId())
				.toList();

			assertAll(
				() -> assertThat(result).hasSize(hasNotEvalautionCount),
				() -> assertThat(result).containsAll(hasNotEvaluationApplicationIds)
			);
		}
	}

	@Nested
	class SortTest {

		@DisplayName("생성일 기준 내림차순 정렬한다")
		@Test
		void findAllWithStatusByProcess_OrderByCreatedAtDesc() {
			ApplicationCondition condition = new ApplicationCondition(
				SortType.APPLIED,
				EvaluationFilter.ALL,
				ResultFilter.ALL,
				PageRequest.of(0, 5)
			);

			List<UUID> applicationIds = applicationRepository.findAllWithStatusByProcess(
					process, condition, condition.pageRequest())
				.getContent()
				.stream()
				.map(applicationWithStatus -> applicationWithStatus.application().getId())
				.toList();

			assertThat(applicationIds).containsExactlyElementsOf(
				List.of(
					applications.get(4).getId(),
					applications.get(3).getId(),
					applications.get(2).getId(),
					applications.get(1).getId(),
					applications.get(0).getId()
				)
			);
		}

		@DisplayName("이름 기준 오름차순 정렬한다")
		@Test
		void findAllWithStatusByProcess_OrderByNameAsc() {
			ApplicationCondition condition = new ApplicationCondition(
				SortType.NAME,
				EvaluationFilter.ALL,
				ResultFilter.ALL,
				PageRequest.of(0, 5)
			);

			List<String> result = applicationRepository.findAllWithStatusByProcess(
					process, condition, condition.pageRequest())
				.getContent()
				.stream()
				.map(applicationWithStatus -> applicationWithStatus.application().getUserName())
				.toList();

			assertThat(result).containsExactlyElementsOf(
				List.of(
					"김성민",
					"나아연",
					"이근표",
					"이한별",
					"조혜원"
				)
			);
		}
	}
}
