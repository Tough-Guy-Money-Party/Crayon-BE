package com.yoyomo.domain.application.domain.repository;

import java.nio.ByteBuffer;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.application.exception.BatchInsertFailException;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ApplicationJdbcRepository {

	public static final int BATCH_SIZE = 50;

	private final JdbcTemplate jdbcTemplate;

	public List<Application> batchInsert(List<Application> applications) {
		int total = applications.size();

		for (int start = 0; start < total; start += BATCH_SIZE) {
			int end = Math.min(start + BATCH_SIZE, total);
			List<Application> chunk = applications.subList(start, end);

			int[] results = jdbcTemplate.batchUpdate(
				"""
					INSERT INTO application
					(application_id, user_name, email, tel, recruitment_id, process_id, created_at)
					VALUES (?, ?, ?, ?, ?, ?, ?)
					""",
				new BatchPreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps, int index) throws SQLException {
						Application application = chunk.get(index);
						UUID applicationId = application.generateId();

						ps.setBytes(1, uuidToBytes(applicationId));
						ps.setString(2, application.getUserName());
						ps.setString(3, application.getEmail());
						ps.setString(4, application.getTel());
						ps.setBytes(5, uuidToBytes(application.getRecruitmentId()));
						ps.setLong(6, application.getProcess().getId());
						ps.setTimestamp(7, Timestamp.valueOf(application.getCreatedAt()));
					}

					@Override
					public int getBatchSize() {
						return chunk.size();
					}
				}
			);

			if (results.length != chunk.size()) {
				throw new BatchInsertFailException();
			}
		}

		return new ArrayList<>(applications);
	}

	private byte[] uuidToBytes(UUID uuid) {
		ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
		bb.putLong(uuid.getMostSignificantBits());
		bb.putLong(uuid.getLeastSignificantBits());
		return bb.array();
	}
}
