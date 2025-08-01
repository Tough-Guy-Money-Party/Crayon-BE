package com.yoyomo.domain.application.domain.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.yoyomo.domain.application.exception.AccessDeniedException;
import com.yoyomo.domain.recruitment.domain.entity.Process;
import com.yoyomo.domain.recruitment.domain.entity.enums.Type;
import com.yoyomo.domain.user.domain.entity.User;
import com.yoyomo.global.common.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(uniqueConstraints =
	{
		@UniqueConstraint(columnNames =
			{
				"recruitment_id", "user_id"
			}
		)
	}
)
public class Application extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "application_id")
	private UUID id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	private String userName;

	private String email;

	@Column(length = 13)
	private String tel;

	@Column(nullable = false, name = "recruitment_id")
	private UUID recruitmentId;

	@ManyToOne
	@JoinColumn(name = "process_id")
	private Process process;

	@Embedded
	private Interview interview;

	private LocalDateTime deletedAt;

	@Builder
	public Application(
		LocalDateTime createdAt,
		User user,
		String userName,
		String email,
		String tel,
		UUID recruitmentId,
		Process process
	) {
		super(createdAt, createdAt);
		this.user = user;
		this.userName = userName;
		this.email = email;
		this.tel = tel;
		this.recruitmentId = recruitmentId;
		this.process = process;
	}

	public UUID generateId() {
		this.id = UUID.randomUUID();
		return id;
	}

	public void update(Process process) {
		this.process = process;
	}

	public void addInterview(Interview interview) {
		this.interview = interview;
	}

	public void delete() {
		this.deletedAt = LocalDateTime.now();
	}

	public void checkAuthorization(User user) {
		if (!this.user.equals(user)) {
			throw new AccessDeniedException();
		}
	}

	public boolean isBeforeInterview(List<Type> types) {

		if (!types.contains(Type.INTERVIEW)) {
			return false;
		}

		return types.indexOf(Type.INTERVIEW) > this.getProcess().getStage();
	}
}
