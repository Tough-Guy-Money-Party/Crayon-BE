package com.yoyomo.domain.application.domain.repository;

import static com.yoyomo.domain.fixture.TestFixture.*;
import static org.assertj.core.api.Assertions.*;

import java.nio.ByteBuffer;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;

import com.yoyomo.domain.RepositoryTest;
import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.application.domain.entity.ProcessResult;
import com.yoyomo.domain.application.domain.entity.enums.Status;
import com.yoyomo.domain.recruitment.domain.entity.Process;
import com.yoyomo.domain.recruitment.domain.repository.ProcessRepository;
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
	JdbcTemplate jdbcTemplate;

	private static byte[] uuidToBytes(UUID uuid) {
		ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
		bb.putLong(uuid.getMostSignificantBits());
		bb.putLong(uuid.getLeastSignificantBits());
		return bb.array();
	}

	@DisplayName("생성일 기준 내림차순 정렬한다")
	@Test
	void findAllWithStatusByProcess() {
		Process process = processRepository.save(process());

		IntStream.range(0, 10)
			.mapToObj(i -> userRepository.save(user()))
			.forEach(user -> jdbcTemplate.update(
				"INSERT INTO application(application_id, user_id, process_id, created_at, recruitment_id) VALUES (?, ?, ?, ?, ?)",
				new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps) throws SQLException {
						ps.setBytes(1, uuidToBytes(UUID.randomUUID()));
						ps.setLong(2, user.getId());
						ps.setLong(3, process.getId());
						ps.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
						ps.setBytes(5, uuidToBytes(UUID.randomUUID()));
					}
				}));

		List<UUID> applications = applicationRepository.findAll()
			.stream()
			.map(Application::getId)
			.toList();

		List<ProcessResult> processResults = List.of(
			processResult(applications.get(0), process.getId(), Status.DOCUMENT_PASS),
			processResult(applications.get(1), process.getId(), Status.PENDING),
			processResult(applications.get(3), process.getId(), Status.DOCUMENT_FAIL),
			processResult(applications.get(4), process.getId(), Status.PENDING),
			processResult(applications.get(5), process.getId(), Status.PENDING),
			processResult(applications.get(7), process.getId(), Status.DOCUMENT_PASS),
			processResult(applications.get(8), process.getId(), Status.DOCUMENT_FAIL),
			processResult(applications.get(9), process.getId(), Status.DOCUMENT_FAIL)
		);

		processResultRepository.saveAll(processResults);

		List<UUID> applicationIds = applicationRepository.findAllWithStatusByProcess(process, PageRequest.of(0, 7))
			.getContent()
			.stream()
			.map(applicationWithStatus -> applicationWithStatus.application().getId())
			.toList();

		assertThat(applicationIds).containsExactlyElementsOf(
			List.of(
				applications.get(9),
				applications.get(8),
				applications.get(7),
				applications.get(6),
				applications.get(5),
				applications.get(4),
				applications.get(3)
			)
		);

		List<UUID> applicationIds2 = applicationRepository.findAllWithStatusByProcess(process, PageRequest.of(1, 7))
			.getContent()
			.stream()
			.map(applicationWithStatus -> applicationWithStatus.application().getId())
			.toList();

		assertThat(applicationIds2).containsExactlyElementsOf(
			List.of(
				applications.get(2),
				applications.get(1),
				applications.get(0)
			)
		);
	}
}
