package com.yoyomo.domain.application.domain.repository;

import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
import com.yoyomo.domain.application.domain.entity.ProcessResult;
import com.yoyomo.domain.application.domain.entity.enums.Status;
import com.yoyomo.domain.application.domain.repository.dto.ApplicationWithStatus;
import com.yoyomo.domain.recruitment.domain.entity.Process;
import com.yoyomo.domain.recruitment.domain.repository.ProcessRepository;
import com.yoyomo.domain.user.domain.repository.UserRepository;

import static com.yoyomo.domain.fixture.TestFixture.process;
import static com.yoyomo.domain.fixture.TestFixture.processResult;
import static com.yoyomo.domain.fixture.TestFixture.user;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

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
	JdbcTemplate jdbcTemplate;

	private Process process;
	private List<Application> applications;

	@BeforeEach
	void setUp() {
		process = processRepository.save(process());

		IntStream.range(0, 5)
			.mapToObj(i -> userRepository.save(user()))
			.forEach(user -> jdbcTemplate.update(
				"INSERT INTO application(application_id, user_id, process_id, created_at, recruitment_id) VALUES (?, ?, ?, ?, ?)",
				ps -> {
					ps.setBytes(1, uuidToBytes(UUID.randomUUID()));
					ps.setLong(2, user.getId());
					ps.setLong(3, process.getId());
					ps.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
					ps.setBytes(5, uuidToBytes(UUID.randomUUID()));
				}));

		applications = applicationRepository.findAll()
			.stream()
			.toList();

		List<ProcessResult> processResults = List.of(
			processResult(applications.get(0).getId(), process.getId(), Status.DOCUMENT_PASS),
			processResult(applications.get(1).getId(), process.getId(), Status.PENDING),
			processResult(applications.get(2).getId(), process.getId(), Status.DOCUMENT_FAIL),
			processResult(applications.get(3).getId(), process.getId(), Status.BEFORE_EVALUATION),
			processResult(applications.get(4).getId(), process.getId(), Status.PENDING)
		);

		processResultRepository.saveAll(processResults);
	}

	private byte[] uuidToBytes(UUID uuid) {
		ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
		bb.putLong(uuid.getMostSignificantBits());
		bb.putLong(uuid.getLeastSignificantBits());
		return bb.array();
	}

	@DisplayName("생성일 기준 내림차순 정렬한다")
	@Test
	void findAllWithStatusByProcess_OrderByCreatedAt() {
		ApplicationCondition condition = new ApplicationCondition(
			SortType.APPLIED,
			EvaluationFilter.ALL,
			ResultFilter.ALL,
			PageRequest.of(0, 5)
		);

		List<UUID> applicationIds = applicationRepository.findAllWithStatusByProcess(process, condition,
				condition.pageRequest())
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

	@DisplayName("합격 지원서만 필터링한다")
	@Test
	void findAllWithStatusByProcess_filterPass() {
		ApplicationCondition condition = new ApplicationCondition(
			SortType.APPLIED,
			EvaluationFilter.ALL,
			ResultFilter.PASS,
			PageRequest.of(0, 5)
		);

		List<ApplicationWithStatus> passApplications = applicationRepository.findAllWithStatusByProcess(process,
				condition, condition.pageRequest())
			.getContent()
			.stream()
			.toList();

		assertAll(
			() -> assertThat(passApplications).hasSize(1),
			() -> assertThat(passApplications.get(0).application().getId()).isEqualTo(applications.get(0).getId()),
			() -> assertThat(passApplications.get(0).status().isPass()).isTrue()
		);
	}
}
